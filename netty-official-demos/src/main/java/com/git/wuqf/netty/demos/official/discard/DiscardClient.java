package com.git.wuqf.netty.demos.official.discard;

import com.git.wuqf.netty.demos.official.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class DiscardClient implements Client {
    private String ip;
    private int port;

    public DiscardClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardClientHandler());
                }
            });
            ChannelFuture f = b.connect(ip, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args){
        DiscardClient client=new DiscardClient("localhost",9999);
        client.connect();
    }
}
