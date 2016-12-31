package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wuqf on 16-12-30.
 */
public class MTClientDispatchHandler extends ChannelInboundHandlerAdapter {

    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//
//        Request request=new Request("1af");
//        ctx.writeAndFlush(request);
//        responseMap.putIfAbsent(request.getMessageId(), new LinkedBlockingQueue<Response>(1));
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response=null;
        if(msg instanceof Response){
            response=(Response)msg;
            BlockingQueue<Response> queue = responseMap.get(response.getMessageId());
            queue.add(response);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public Response getResponse(final long messageId) {
        Response result=null;
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

