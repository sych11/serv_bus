package org.prof_itgroup.it_juridique;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.prof_itgroup.it_juridique.json.driver.Driver;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.prof_itgroup.it_juridique.json.result.ServiceResult;

import org.json.JSONObject;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class Juridique implements MessageListener{

private static Logger log =Logger.getLogger(Juridique.class.getName());
	//@Override
	/*public void goGet(Message message,String url, Map<String, String> headers, String charSet) throws Exception {
		String res = "";
		url="http://localhost:8181/it-rest/it/driver";
		headers.put("Content-Type", "text/xml;charset=UTF-8");
        charSet="Cp1251";

        ServiceResult result= new ServiceResult();


		String request="";
		ObjectMapper mapper = new ObjectMapper();
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			request=payload;
			Driver driver = mapper.readValue(payload, Driver.class);
		}catch (Exception e) {

		}


		try {
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();

			RestTemplate rest = new RestTemplate(httpRequestFactory);//экземпляр, RestTemplate основанный на данных ClientHttpRequestFactory.
			rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(charSet)));//Возвращает список конвертеров тела сообщения

			HttpHeaders httpHeaders = new HttpHeaders();//заголовок http
			Set<Entry<String, String>> entrySet = headers.entrySet();//в headers устонавливаем запись
			for (Entry<String, String> entry : entrySet) { //перебираем записи коллекции entry в коллекции entrySet
				httpHeaders.add(entry.getKey(), entry.getValue());//добавляем знач ключа(Content-Type) и записи(UTF-8) в httpHeaders
			}



			HttpEntity<String> requestEntity= new HttpEntity<String>(request, httpHeaders); //создаем объект Http Entity с пустым телом (запроса) и заголовком
			ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);//отправка запроса методом get, по url, и получение ответа в виде типа строки
			res = requestEntity.getBody();//получаем тело ответа и присваиваем его строке res
			//res=res.substring(res.indexOf("<?xml"));


            result.setSucces(true);

		} catch (Exception e) {

			result.setSucces(false);
			throw e;
		}

	}*/

	@Override
	public void onMessage(Message message) {
		String request="";
		String res = "";
		String url="http://localhost:8181/it-rest/it/driver";
		Map <String,String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "text/xml;charset=UTF-8");
		//String charSet="Cp1251";
		Driver driver=new Driver();

		ServiceResult result= new ServiceResult();


		ObjectMapper mapper = new ObjectMapper();
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			request=payload;
			driver = mapper.readValue(payload, Driver.class);
		}catch (Exception e) {
			log.info(e.getMessage());
		}

		try {
			HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();

			RestTemplate rest = new RestTemplate(httpRequestFactory);//экземпляр, RestTemplate основанный на данных ClientHttpRequestFactory.
			//rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(charSet)));//Возвращает список конвертеров тела сообщения

			HttpHeaders httpHeaders = new HttpHeaders();//заголовок http
			Set<Entry<String, String>> entrySet = headers.entrySet();
			for (Entry<String, String> entry : entrySet) {
				httpHeaders.add(entry.getKey(), entry.getValue());
			}



			JSONObject jsonObject= new JSONObject(request);//json в виде строки
			request=jsonObject.put("jur","").toString();// добавляем ключ jur с пустым знач в json и переводим json в строку

			HttpEntity<String> requestEntity= new HttpEntity<String>(request); //создаем объект Http Entity с request телом (запроса)
			result = rest.postForObject(url,requestEntity,ServiceResult.class);//получаем ответ в формате ServiceResult при отправке запроса методом POST в котором указываем url запроса, сущность запроса с телом и тип ответа
			//res = requestEntity.getBody();//получаем тело ответа и присваиваем его строке res
			//res=res.substring(res.indexOf("<?xml"));

            Boolean success=result.getSucces();

            if(success) {
                log.info("Объект принят");
            } else {
                log.info("Объект не был получен");
            }


		//	result.getSucces();

		} catch (Exception e) {
            log.info(e.getMessage());
			result.setSucces(false);
		}
	}




	/*public static void main(String[] args) {
		HttpRestServiceImpl httpRestService= new HttpRestServiceImpl();
		System.out.println(httpRestService.res);
	}*/

}
