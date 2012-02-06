package com.vinci.netty.time;

import com.vinci.netty.time.pojo.UnixTime;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import static org.jboss.netty.buffer.ChannelBuffers.buffer;

public class TimeEncoder extends SimpleChannelHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        UnixTime time = (UnixTime) e.getMessage();

        ChannelBuffer buf = buffer(4);
        buf.writeInt(time.getValue());

        Channels.write(ctx, e.getFuture(), buf);
    }
}
