package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.exchange.Response;
import com.git.wuqf.netty.framework.server.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by wuqf on 16-12-30.
 */
public class NettyClientDispatchHandler extends SimpleChannelInboundHandler<Response> {

    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();

//    @Override
//    public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
//        if (msg instanceof Request) {
//            Request request = (Request) msg;
//            responseMap.putIfAbsent(request.getMessageId(), new LinkedBlockingQueue<Response>(1));
//        }
//        super.write(ctx, msg, promise);
//    }
//
//    @Override
//    protected void messageReceived(final ChannelHandlerContext ctx, final Response response) throws Exception {
//        BlockingQueue<Response> queue = responseMap.get(response.getMessageId());
//        queue.add(response);
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        BlockingQueue<Response> queue = responseMap.get(response.getMessageId());
        queue.add(response);
    }

    public Response getResponse(final long messageId) {
        Response result = null;
        responseMap.putIfAbsent(messageId, new LinkedBlockingQueue<Response>(1));
        try {
            result = responseMap.get(messageId).take();
            if (null == result) {
                result = getSystemMessage();
            }
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            responseMap.remove(messageId);
        }
        return result;
    }

    private Response getSystemMessage() {
        try {
            return responseMap.get(Server.SYSTEM_MESSAGE_ID).poll(5, SECONDS);
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

