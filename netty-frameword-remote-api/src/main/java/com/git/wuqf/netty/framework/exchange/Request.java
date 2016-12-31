package com.git.wuqf.netty.framework.exchange;


import lombok.Getter;

import java.io.Serializable;
@Getter
public final class Request implements Serializable {
	
	private static final long serialVersionUID = 2750646443189480771L;
	
	private final long messageId;
	private Object value;
	
	public Request(Object value) {
		messageId = System.nanoTime();
		this.value=value;
	}

}
