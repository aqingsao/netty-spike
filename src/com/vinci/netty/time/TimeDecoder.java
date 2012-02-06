package com.vinci.netty.time;

import com.vinci.netty.time.pojo.UnixTime;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class TimeDecoder extends FrameDecoder {
    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        if (channelBuffer.readableBytes() < 4) {
            return null;
        }
        return new UnixTime(channelBuffer.readInt());
    }
}
