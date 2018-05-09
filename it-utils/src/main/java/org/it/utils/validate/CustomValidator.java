package org.it.utils.validate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.it.utils.exception.ExceptionUtil;
import org.it.utils.xml.XmlUtils;
import org.w3c.dom.Node;

public class CustomValidator {
	private static Logger log = Logger.getLogger(CustomValidator.class.getName());
	
	private Map<URL, Schema> shemaMap = new HashMap<URL, Schema>(); 
	
	
	public void validate(Node doc, URL uri) throws Exception{
		try {
			
			Schema schema = shemaMap.get(uri);
			
			if (schema==null){
				SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
				URL schemaLocation = uri;
				schema = factory.newSchema(schemaLocation);
				shemaMap.put(uri, schema);
			}
			Validator validator= schema.newValidator();
			DOMSource source = new DOMSource(doc);
			validator.validate(source);
		}  catch (MalformedURLException e) {
			log.info("CustomValidator MalformedURLException error:"+ExceptionUtil.getPrintStackTraceAsString(e));
			throw e;
		} catch (IOException e) {
			log.info("CustomValidator IOException error:"+ExceptionUtil.getPrintStackTraceAsString(e));
			throw e;
		}
		catch (Exception e){
			XmlUtils utils = new XmlUtils();
			String nodeToString = utils.nodeToString(doc);
			log.info("CustomValidator Exception error:"+ExceptionUtil.getPrintStackTraceAsString(e)+":"+nodeToString+":"+uri.toString());
			throw e;
		}
	}
}	