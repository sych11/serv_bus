package org.it.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.it.utils.exception.ExceptionUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class XmlUtils {
	Logger log = Logger.getLogger(XmlUtils.class.getName());

	private static final ThreadLocal<XPathFactory> XPATH_FACTORY = new ThreadLocal<XPathFactory>() {
		@Override
		protected XPathFactory initialValue() {
			return XPathFactory.newInstance();
		}
	};

	private PersonalNamespaceContext nsContext;

	public XmlUtils() {
		nsContext = new PersonalNamespaceContext();
		nsContext.setPrefix("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
	}

	public XmlUtils(PersonalNamespaceContext nsContext) {
		this.nsContext = nsContext;
	}

	public Document parseMessage(String message) throws Exception {

		Document doc = null;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new InputSource(new StringReader(message)));

		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
			log.info("XmlUtils error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			throw e1;
		} catch (SAXException e1) {
			e1.printStackTrace();
			log.info("XmlUtils error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			throw e1;
		} catch (IOException e1) {
			e1.printStackTrace();
			log.info("XmlUtils error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			throw e1;
		}

		return doc;
	}
	
	public Document parseMessage(InputStream is) throws Exception {

		Document doc = null;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new InputSource(new InputStreamReader(is, "UTF-8")));

		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
			log.info("XmlUtils error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			throw e1;
		} catch (SAXException e1) {
			e1.printStackTrace();
			log.info("XmlUtils error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			throw e1;
		} catch (IOException e1) {
			e1.printStackTrace();
			log.info("XmlUtils error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			throw e1;
		}

		return doc;
	}
	
	public Node getNode(String xpath, Node doc) {
		XPathExpression expr;
		try {
			expr = getXpathTool().compile(xpath);
		} catch (XPathExpressionException e) {
			return null;
		}
		return getNode(expr, doc);
	}
	
	
	public Document getNodeAsDocument(String xpath, Node doc) {
		XPathExpression expr;
		try {
			expr = getXpathTool().compile(xpath);
		} catch (XPathExpressionException e) {
			return null;
		}
		return getNodeAsDocument(expr, doc);
	}
	
	private Document getNodeAsDocument(XPathExpression expr, Node node) {
		Node resNode = null;
		Document document = null;
		try {
			resNode = (Node) expr.evaluate(node, XPathConstants.NODE);
			if (resNode != null) {
				try {
					DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
					newInstance.setNamespaceAware(true);
					document = newInstance.newDocumentBuilder().newDocument();
					Node dup = document.importNode(resNode, true);
					document.appendChild(dup);
				} catch (ParserConfigurationException e) {
					log.info("getNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
				}

			}
		} catch (XPathExpressionException e) {
			log.info("getNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return document;
	}
	
	private Node getNode(XPathExpression expr, Node doc) {
		Node node = null;
		try {
			node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			log.info("getNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return node;
	} 
	

	public String getText(String xpath, Node doc) {

		XPathExpression expr;
		try {
			expr = getXpathTool().compile(xpath);
		} catch (XPathExpressionException e) {
			return null;
		}
		return getText(expr, doc);
	}

	public Integer getIntValue(String xpath, Node doc) {
		XPathExpression expr;
		Number value = null;
		try {
			expr = getXpathTool().compile(xpath);
			value = (Number) expr.evaluate(doc, XPathConstants.NUMBER);
		} catch (XPathExpressionException e) {
			log.info("getIntValue error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			return null;
		}
		return value.intValue();
	}

	public String getStringValue(String xpath, Document doc) {
		XPathExpression expr;
		String value = null;
		try {
			expr = getXpathTool().compile(xpath);
			value = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			log.info("getStringValue error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			return null;
		}
		return value;
	}
	public String getStringValue(String xpath, Node doc) {
		XPathExpression expr;
		String value = null;
		try {
			expr = getXpathTool().compile(xpath);
			value = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			log.info("getStringValue error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			return null;
		}
		return value;
	}

	
	public NodeList getNodeList(String xpath, Node doc){
		NodeList nodeList = null;
		XPathExpression expr;
		try {
			expr = getXpathTool().compile(xpath);
		} catch (XPathExpressionException e) {
			return null;
		}
		try {
			nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			log.info("getNodeList error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return nodeList;
	}

	public boolean isExist(String xpath, Node doc) {
		XPathExpression expr;
		try {
			expr = getXpathTool().compile(xpath);
		} catch (XPathExpressionException e) {
			log.info("isExist error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			return false;
		}
		Node node = getNode(expr, doc);
		if (node != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String nodeToStringWithoutTraverse(Node node) {
		String res = "";
		
		StringWriter writer = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(node), new StreamResult(writer));
		} catch (TransformerConfigurationException e) {
			log.info("nodeToString error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		} catch (TransformerException e) {
			log.info("nodeToString error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		res = writer.toString();
		return res;
	}

	public String nodeToString(Node node) {
		String res = "";
		ArrayList<String> cdataList = new ArrayList<String>();
		traverse(node, cdataList);
		StringWriter writer = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, joinList(cdataList, " "));
			transformer.transform(new DOMSource(node), new StreamResult(writer));
		} catch (TransformerConfigurationException e) {
			log.info("nodeToString error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		} catch (TransformerException e) {
			log.info("nodeToString error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		res = writer.toString();
		return res;
	}
	
	
	public String nodeToString(Node node, String cdataFields) {
		String res = "";
		StringWriter writer = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, cdataFields);
			transformer.transform(new DOMSource(node), new StreamResult(writer));
		} catch (TransformerConfigurationException e) {
			log.info("nodeToString error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		} catch (TransformerException e) {
			log.info("nodeToString error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		res = writer.toString();
		return res;
	} 
	
	private String joinList(List<String> cdataList, String delimeter){
		String res = "";
		StringBuilder builder = new StringBuilder();
		for (String cdata: cdataList){
			builder.append(cdata).append(delimeter);
		}
		if (builder.length()>0){
			res = builder.deleteCharAt(builder.length()-1).toString(); //delete last delimeter
		}
		return res;
	} 
	
	public Document newDom(){
		DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
		newInstance.setNamespaceAware(true);
		Document doc = null;
		try {
			 doc = newInstance.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			log.info("newDom error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return doc;
	}
	
	private String getText(XPathExpression expr, Node doc) {
		try {
			Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
			if (node == null)
				return null;
			return node.getTextContent();

		} catch (XPathExpressionException e) {
			log.info("XPathConverter error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			return null;
		}
	}

	public Node modifyNode(String xpath, String value, Node doc) {

		try {
			NodeList myNodeList = (NodeList) getXpathTool().evaluate(xpath, doc, XPathConstants.NODESET);
			if (myNodeList != null && myNodeList.getLength() > 0) {
				String textContent = myNodeList.item(0).getTextContent();
				if (textContent != null) {
					if (value != null && !"".equals(value)) {
						myNodeList.item(0).setTextContent(value);
					}
				}
			}
		} catch (XPathExpressionException e) {
			log.info("modifyNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return doc;
	}
	
	public Node removeNode(String xpath, Node doc){
		try {
			Node node = (Node) getXpathTool().evaluate(xpath, doc, XPathConstants.NODE);
			Node parentNode = node.getParentNode();
			if (parentNode!=null){
				parentNode.removeChild(node);
			}
		} catch (XPathExpressionException e) {
			log.info("modifyNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return doc;
	}

	public Node addNode(String xpath, String value, Node childNode, Node doc) {

		try {
			NodeList myNodeList = (NodeList) getXpathTool().evaluate(xpath, doc, XPathConstants.NODESET);
			if (myNodeList != null && myNodeList.getLength() > 0) {
				String textContent = myNodeList.item(0).getTextContent();
				if (textContent == null || "".equals(textContent)) {
					if (value != null && !"".equals(value)) {
						myNodeList.item(0).appendChild(childNode);
					}
				}
			}
		} catch (XPathExpressionException e) {
			log.info("addNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return doc;
	}
	
	public Node replaceNode(String xpath, Node newNode, Document doc){
		try {
			Node node = (Node) getXpathTool().evaluate(xpath, doc, XPathConstants.NODE);
			Node parentNode = node.getParentNode();
			Node importNode = doc.importNode(newNode, true);
			parentNode.replaceChild(importNode, node);
		}catch (XPathExpressionException e) {
			log.info("replaceNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return doc;
	}

	public Node addNode(String xpath, Node childNode, Document doc) {
		try {
			Node node  = (Node)getXpathTool().evaluate(xpath, doc, XPathConstants.NODE);
			Node importNode = doc.importNode(childNode, true);
			node.appendChild(importNode);
			
		} catch (XPathExpressionException e) {
			log.info("addNode error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return doc;
		
	}

	public Document modifyAttribute(String xpath, String attributeName, String value, Document doc) {

		try {
			NodeList myNodeList = (NodeList) getXpathTool().evaluate(xpath, doc, XPathConstants.NODESET);
			if (myNodeList != null && myNodeList.getLength() > 0) {
				Node node = myNodeList.item(0);
				NamedNodeMap attributes = node.getAttributes();
				Node namedItem = attributes.getNamedItem(attributeName);
				if (namedItem != null) {
					namedItem.setTextContent(value);
				}
			}
		} catch (XPathExpressionException e) {
			log.info("modifyAttribute error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return doc;
	}
	
	public Node modifyAttribute(String xpath, String attributeName, String value, Node doc) {

		try {
			Element node  = (Element)getXpathTool().evaluate(xpath, doc, XPathConstants.NODE);
			if (node!=null){
				NamedNodeMap attributes = node.getAttributes();
				Node namedItem = attributes.getNamedItem(attributeName);
				if (namedItem != null) {
					namedItem.setTextContent(value);
				}
			}
			
		} catch (XPathExpressionException e) {
			log.info("modifyAttribute error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return doc;
	}

	public XPath getXpathTool() {
		XPath xpathTool = XPATH_FACTORY.get().newXPath();
		xpathTool.setNamespaceContext(nsContext);
		return xpathTool;
	}

	public String escapeXml(String s) {
		String replaceAll = "";
		if (s != null) {
			replaceAll = s.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
		}
		return replaceAll;
	}
	
	public void traverse(Node node, List<String> cdata) {
		if (node == null) {
			return;
		}
		int type = node.getNodeType();
		if (type == Node.DOCUMENT_NODE) {
			traverse(((Document) node).getDocumentElement(), cdata);
		} else if (type == Node.ELEMENT_NODE) {
			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();
				for (int i = 0; i < len; i++) {
					traverse(children.item(i), cdata);
				}
			}
		} else if (type == Node.ENTITY_REFERENCE_NODE) {
			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();
				for (int i = 0; i < len; i++) {
					traverse(children.item(i), cdata);
				}
			}
		} else if (type == Node.CDATA_SECTION_NODE) {
			Element e = (Element)node.getParentNode();
			if (e!=null){
				if (e.getPrefix()!=null){
					cdata.add(e.getLocalName());
				}else{
					cdata.add(e.getTagName());
				}
			}

		}
	}
 

}
