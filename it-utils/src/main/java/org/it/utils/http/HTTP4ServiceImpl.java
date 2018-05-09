package org.it.utils.http;

import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTP4ServiceImpl implements HTTPService {
		
	//@Override
	public String goGet(String url, Map<String, String> headers, int timeout, String charSet, HTTPLogListener logListener) throws Exception {
		// TODO Auto-generated method stub
		String res = "";
		CloseableHttpClient createDefault = HttpClients.createDefault();

		logListener.request(url, headers, null);
		try {
			try {
				HttpGet get = new HttpGet(url);
				RequestConfig requestConfig = null;

				requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout)
						.setSocketTimeout(timeout).build();

				get.setConfig(requestConfig);
				Set<String> keySet = headers.keySet();
				for (String header : keySet) {
					get.addHeader(header, headers.get(header));
				}
				CloseableHttpResponse response = createDefault.execute(get);
				try {
					// Get hold of the response entity
					HttpEntity ht = response.getEntity();
					BufferedHttpEntity buf = new BufferedHttpEntity(ht);
					res = EntityUtils.toString(buf, charSet);

				} finally {
					if (response != null) {
						response.close();
					}
				}
				logListener.response(url, headers, null, res);

			} finally {
				createDefault.close();
			}
		} catch (Exception e) {
			logListener.exception(url, headers, null, e);
			throw e;
		}
		return res;
	}

}
