package com.git.wuqf.netty.framework.client;

/**
 * Created by wuqf on 16-12-31.
 */
public class NettyClientTest {

    static NettyClient nettyClient = new NettyClient();

    public static void main(String[] args) throws Throwable {
        nettyClient.connect();
//        for(int i=0;i<10;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    excutor();
//                }
//            }).run();
//        }
        excutor();
    }
    public static void excutor(){
//        for(int i=0;i<10000;i++){
//            Request request=new Request(String.class,"xx");
//            nettyClient.send(request);
//        }
        nettyClient.send("abc\r\n");
    }
}
