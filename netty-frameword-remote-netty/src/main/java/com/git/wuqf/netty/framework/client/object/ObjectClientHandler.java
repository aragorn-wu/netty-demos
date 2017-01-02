package com.git.wuqf.netty.framework.client.object;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqf on 16-12-30.
 */
public class ObjectClientHandler extends ChannelInboundHandlerAdapter {
    
    private final List<Integer> firstMessage;

    /**
     * Creates a client-side handler.
     */
    public ObjectClientHandler() {
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

