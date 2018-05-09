package org.prof_itgroup.it_cbr_fourpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.properties.PropertiesService;

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
		try {
			InputStream inputStream = new FileInputStream("./etc/it.cbr.fourpl.config.cfg");
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
