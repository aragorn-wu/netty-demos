package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.client.cs.CSClient;

/**
 * Created by wuqf on 16-12-31.
 */
public class NettyClientTest {

    static CSClient nettyClient = new CSClient();

    public static void main(String[] args) throws Throwable {
        nettyClient.connect();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    excutor();
                }
            }).run();
        }
    }

    public static void excutor() {
        for (int i = 0; i < 100000; i++) {
            String messageId = Thread.currentThread().getContextClassLoader() + "-----" + i;
            String request = messageId + "," + i + "," + (i + 1) + "\r\n";

            String response = nettyClient.send(request);
            if (response == null) {
                assert false;
            } else if (!messageId.equals(response.split(",")[0])) {
                assert false;
            }
        }

    }
}
