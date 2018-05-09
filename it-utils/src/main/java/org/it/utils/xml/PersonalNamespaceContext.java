package org.it.utils.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;




public class PersonalNamespaceContext implements NamespaceContext {
	private Map<String, String> prefixMap;
	
	public PersonalNamespaceContext(){
		prefixMap = new HashMap<String, String>();
	}

    public void setPrefixMap(HashMap<String, String> prefixMap) {
    	this.prefixMap = prefixMap;
		this.prefixMap.put("xml", XMLConstants.XML_NS_URI);
	}

	public String getNamespaceURI(String prefix) {
        if (prefix == null) throw new NullPointerException("Null prefix");
        String namespace = prefixMap.get(prefix);
        if (namespace==null){
        	return XMLConstants.NULL_NS_URI;
        }
        return namespace;
    }
    public void setPrefix(String prefix, String namespace){
    	prefixMap.put(prefix, namespace);
    }

    // This method isn't necessary for XPath processing.
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }

    // This method isn't necessary for XPath processing either.
    public Iterator<?> getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }
    
}
