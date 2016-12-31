package com.git.wuqf.netty.framework.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ObjectServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private NettyServerDispatchHandler serverDispatchHandler;
	
	@Override
	protected void initChannel(final SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new ObjectEncoder());
		ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
		ch.pipeline().addLast(serverDispatchHandler);
	}
}
