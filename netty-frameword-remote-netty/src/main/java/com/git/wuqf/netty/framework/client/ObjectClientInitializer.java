package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wuqf on 16-12-30.
 */
public class ObjectClientInitializer extends ChannelInitializer<SocketChannel> {

    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
    }

    public Response getResponse(final long messageId) {
        Response result = null;
        responseMap.putIfAbsent(messageId, new LinkedBlockingQueue<Response>(1));
        try {
            result = responseMap.get(messageId).take();

        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            responseMap.remove(messageId);
        }
        return result;
    }
}
