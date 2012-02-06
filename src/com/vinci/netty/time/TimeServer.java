package com.vinci.netty.time;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;

import static java.util.concurrent.Executors.*;

public class TimeServer {
    static final ChannelGroup allChannels = new DefaultChannelGroup("time-server");

    public static void main(String[] args) {
        ChannelFactory factory =
                new NioServerSocketChannelFactory(
                        newCachedThreadPool(),
                        newCachedThreadPool());

        ServerBootstrap bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                return Channels.pipeline(new TimeEncoder(), new TimeServerHandler());
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        Channel channel = bootstrap.bind(new InetSocketAddress(8080));
        allChannels.add(channel);
        try {
            Thread.sleep(10 * 1000L);
        } catch (Exception e) {

        }

        ChannelGroupFuture future = (ChannelGroupFuture) allChannels.close();
        future.awaitUninterruptibly();
        factory.releaseExternalResources();
    }
}
