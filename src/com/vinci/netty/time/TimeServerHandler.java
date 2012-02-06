package com.vinci.netty.time;

import com.vinci.netty.time.pojo.UnixTime;
import org.jboss.netty.channel.*;

public class TimeServerHandler extends SimpleChannelHandler {
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        TimeServer.allChannels.add(e.getChannel());
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        UnixTime time = new UnixTime((int) (System.currentTimeMillis() / 1000));

        ChannelFuture f = e.getChannel().write(time);

        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                Channel ch = future.getChannel();
                ch.close();
            }
        });
    }
}
