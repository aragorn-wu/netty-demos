package com.git.wuqf.netty.framework.server.multithread;

import com.git.wuqf.netty.framework.exchange.Request;
import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wuqf on 16-12-31.
 */
@ChannelHandler.Sharable
public class MTServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Request request=null;
        if(msg instanceof Request){
            request=(Request)msg;
        }
        ctx.writeAndFlush(new Response(request.getMessageId(),request.getValue()));
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
