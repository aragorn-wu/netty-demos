package com.git.wuqf.netty.framework.exchange;

import lombok.Getter;

import java.io.Serializable;

@Getter
public final class Response implements Serializable {

    private static final long serialVersionUID = 5887232731148682128L;

    private final String messageId;
    private final Object returnValue;
    private final Throwable exception;

    public Response(final String messageId, final Object returnValue) {
        this.messageId = messageId;
        this.returnValue = returnValue;
        this.exception = null;
    }

    public Response(final String messageId, final Throwable exception) {
        this.messageId = messageId;
        this.returnValue = null;
        this.exception = exception;
    }
}
