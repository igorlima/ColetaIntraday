package com.eatj.igorribeirolima.coletaintraday.util;

import org.apache.http.protocol.HttpContext;

public interface Login{
	HttpContext getHttpContext();
	boolean login();
	void unauthorized();
}
