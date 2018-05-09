package org.prof_itgroup.it_juridique;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.properties.PropertiesService;

import java.io.*;
import java.util.Properties;

public class PropertiesServiceImpl implements PropertiesService{

	private static Logger log = Logger.getLogger(PropertiesServiceImpl.class.getName());

	private Properties init() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		Reader reader = null;
		Properties propertyFSSP = new Properties();
		try {
			InputStream inputStream = new FileInputStream("./etc/it.base.config.cfg");
			reader = new InputStreamReader(inputStream, "UTF-8");
			propertyFSSP.load(reader);
		} finally {
			if (reader != null)
				reader.close();
		}

		return propertyFSSP;
	}

	public String get(String key) {
		String res = "";
		try {
			Properties propertyFSSP = init();
			res = propertyFSSP.getProperty(key);
			if (res == null || "".equals(res)) {
				log.info(PropertiesServiceImpl.class.getName() + ":" + "property with name: " + key + " not found");
			}
		} catch (Exception e) {
			log.info(PropertiesServiceImpl.class.getName() + ":" + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return res;
	}

}
