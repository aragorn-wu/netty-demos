/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.exchange.Request;
import com.git.wuqf.netty.framework.exchange.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient {


    private Bootstrap bootstrap;

    private volatile Channel channel; // volatile, please copy reference to use

    private EventLoopGroup workerGroup;

    public void connect() throws Throwable {

        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap()
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true).handler(new TelnetClientInitializer());
        channel = bootstrap.connect("localhost", 6666).sync().channel();

    }

    public Response send(final Request request) {
        channel.writeAndFlush(request);
        return null;
    }

    public Response send(final String request) {
        channel.writeAndFlush(request);
        return null;
    }


    public void close() {
        if (null == channel) {
            return;
        }
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        workerGroup = null;
        channel = null;
    }
}