package org.it.utils.http;

import java.util.Map;


public interface HTTPService {
	public String goGet(String url, Map<String, String> headers, int timeout,String charSet, HTTPLogListener logListener) throws Exception;
	
}
