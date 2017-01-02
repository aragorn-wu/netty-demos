package com.git.wuqf.netty.framework.client.multithread;

import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuqf on 16-12-30.
 */
@ChannelHandler.Sharable
public class MTClientDispatchHandler extends ChannelInboundHandlerAdapter {

    private final ConcurrentHashMap<String, Response> responseMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response=null;
        if(msg instanceof Response){
            response=(Response)msg;
            responseMap.put(response.getMessageId(),response);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public Response getResponse(final String messageId) {
        for(int i=0;i<100;i++){
            if(responseMap.containsKey(messageId)){
                Response response=responseMap.get(messageId);
                responseMap.remove(messageId);
                return response;
            }else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}

