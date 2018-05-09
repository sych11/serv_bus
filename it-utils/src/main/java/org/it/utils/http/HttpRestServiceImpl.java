package org.it.utils.http;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpRestServiceImpl implements HTTPService {

	@Override
	public String goGet(String url, Map<String, String> headers, int timeout, String charSet, HTTPLogListener logListener) throws Exception {
		String res = "";
		logListener.request(url, headers, null);
		try {
			HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
			httpRequestFactory.setConnectTimeout(timeout);
			httpRequestFactory.setReadTimeout(timeout);

			RestTemplate rest = new RestTemplate(httpRequestFactory);
			rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(charSet)));

			HttpHeaders httpHeaders = new HttpHeaders();
			Set<Entry<String, String>> entrySet = headers.entrySet();
			for (Entry<String, String> entry : entrySet) {
				httpHeaders.add(entry.getKey(), entry.getValue());
			}

			HttpEntity<String> requestEntity = new HttpEntity<String>("", httpHeaders);
			ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
			res = responseEntity.getBody();
			logListener.response(url, headers, null, res);

		} catch (Exception e) {
			logListener.exception(url, headers, null, e);
			throw e;
		}
		return res;
	}

}
