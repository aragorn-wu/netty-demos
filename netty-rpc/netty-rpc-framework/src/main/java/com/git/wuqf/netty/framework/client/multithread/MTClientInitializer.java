package com.git.wuqf.netty.framework.client.multithread;

import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by wuqf on 16-12-30.
 */
public class MTClientInitializer extends ChannelInitializer<SocketChannel> {

    private MTClientDispatchHandler handler = new MTClientDispatchHandler();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        pipeline.addLast(handler);
    }

    public Response getResponse(final String messageId) {
        return handler.getResponse(messageId);
    }
}
