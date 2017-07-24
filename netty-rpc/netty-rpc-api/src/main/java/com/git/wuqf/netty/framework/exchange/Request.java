package com.git.wuqf.netty.framework.exchange;


import lombok.Getter;

import java.io.Serializable;

@Getter
public final class Request implements Serializable {

    private static final long serialVersionUID = 2750646443189480771L;

    private final String messageId;
    private Object value;

    public Request(Object value) {
        messageId = String.valueOf(System.nanoTime());
        this.value = value;
    }

    public Request(String messageId, Object value) {
        this.messageId = messageId;
        this.value = value;
    }

}
