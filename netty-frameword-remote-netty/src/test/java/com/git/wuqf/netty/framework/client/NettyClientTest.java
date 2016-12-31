package com.git.wuqf.netty.framework.client;

import com.git.wuqf.netty.framework.exchange.Request;
import com.git.wuqf.netty.framework.exchange.Response;

/**
 * Created by wuqf on 16-12-31.
 */
public class NettyClientTest {

    static NettyClient nettyClient = new NettyClient();

    public static void main(String[] args) throws Throwable {
        nettyClient.connect();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    excutor();
                }
            }).run();
        }
//        excutor();
    }

    public static void excutor(){
        for(int i=0;i<100000;i++){
            Request request=new Request(Thread.currentThread().getContextClassLoader()+"-----"+i,"xxfafa");
            //System.out.println(request.getMessageId());
           Response response= nettyClient.send(request);
        if(request.getMessageId()!=response.getMessageId()){
            assert false;
        }
//            System.out.println(response.getMessageId());
        }

//        nettyClient.send("abc\r\n");
    }
}
