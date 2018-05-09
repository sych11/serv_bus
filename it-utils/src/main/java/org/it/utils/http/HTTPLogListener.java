package org.it.utils.http;

import java.util.Map;

public interface HTTPLogListener {
	void request(String url, Map<String, String> headers, String request);
	void response(String url, Map<String, String> headers, String request,String response);
	void exception(String url, Map<String, String> headers, String request,Throwable ex);
}
