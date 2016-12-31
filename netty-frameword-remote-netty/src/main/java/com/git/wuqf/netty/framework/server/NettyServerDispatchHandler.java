package com.git.wuqf.netty.framework.server;

import com.git.wuqf.netty.framework.exchange.Request;
import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * Created by wuqf on 16-12-31.
 */
public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<Request> {

//    @Override
//    protected void messageReceived(final ChannelHandlerContext ctx, final Request request) {
//        Object returnValue = execute(request);
//        ctx.writeAndFlush(new Response(request.getMessageId(), returnValue));
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        Object returnValue = execute(request);
        ctx.writeAndFlush(new Response(request.getMessageId(), returnValue));
    }

    private Object execute(final Request request) {
        Object result;
        try {
            Object apiInstance = request.getApiClass();
            Method method = apiInstance.getClass().getMethod(request.getMethod(), getParameterTypes(request.getParameters()));
            result = method.invoke(apiInstance, request.getParameters());
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    private Class<?>[] getParameterTypes(final Object[] parameters) {
        Class<?>[] result = new Class<?>[parameters.length];
        int i = 0;
        for (Object each : parameters) {
            result[i] = each.getClass();
            i++;
        }
        return result;
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        Response response = new Response(Server.SYSTEM_MESSAGE_ID, cause);

        ctx.writeAndFlush(response);
    }
}
