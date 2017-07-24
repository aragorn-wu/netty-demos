package com.git.wuqf.netty.framework.server;

public interface Server {

    long SYSTEM_MESSAGE_ID = -1L;

    void start(int port);

    void stop();
}
