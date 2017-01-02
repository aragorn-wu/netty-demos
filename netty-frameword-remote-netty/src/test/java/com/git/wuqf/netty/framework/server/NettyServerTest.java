package com.git.wuqf.netty.framework.server;

import com.git.wuqf.netty.framework.server.cs.CSServer;

/**
 * Created by wuqf on 16-12-31.
 */
public class NettyServerTest {
    public static void main(String[] args) throws Throwable {
        CSServer nettyServer=new CSServer();
        nettyServer.start();
    }
}
