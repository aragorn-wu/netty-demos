package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuqf on 16-12-30.
 */
public class NettyClientDispatchHandler extends ChannelInboundHandlerAdapter {

    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();

    private final List<Integer> firstMessage;

    /**
     * Creates a client-side handler.
     */
    public NettyClientDispatchHandler() {
        firstMessage = new ArrayList<Integer>(100);
        for (int i = 0; i < 100; i ++) {
            firstMessage.add(Integer.valueOf(i));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the first message if this handler is a client-side handler.
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

