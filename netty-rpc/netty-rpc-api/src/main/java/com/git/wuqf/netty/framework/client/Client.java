package com.git.wuqf.netty.framework.client;


import com.git.wuqf.netty.framework.exchange.Request;
import com.git.wuqf.netty.framework.exchange.Response;

import java.net.InetSocketAddress;

public interface Client {

    void connect(InetSocketAddress socketAddress);

    Response sent(Request request);

    InetSocketAddress getRemoteAddress();

    void close();
}
