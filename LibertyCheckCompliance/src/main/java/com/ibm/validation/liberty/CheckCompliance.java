package com.ibm.validation.liberty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.validation.liberty.bean.EndpointInfo;
import com.ibm.validation.liberty.bean.SSLInfo;

/**
 * Example application to read in an XML file and validate using
 * Bean Validation outside of JEE.
 * 
 * @author Brian S Paskin (IBM Italia)
 * @version 1.0.0.0 (15/02/2019)
 */

public class CheckCompliance {

	public static void main(String[] args) {

		// make sure that the file has been passed
		if ( args.length != 1 ) {
			System.err.println("Path to XML file is required.");
			System.exit(255);
		}
		
		List<String> validationErrors = new ArrayList<String>();

		// Bean validation setup
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		try {
			// Read in XML file and place it in a Document
			File configFile = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configFile);
			doc.getDocumentElement().normalize();
			
			// Read in the features that are installed and 
			// check if the required ones are available
			// no bean validation required
			boolean appSecurity = false, transportSecurity = false;
			NodeList nodeList = doc.getElementsByTagName("feature");
			for (int i = 0; i < nodeList.getLength(); i++) {
					
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {						
					if ( node.getTextContent().equalsIgnoreCase("appSecurity-2.0")) {
						appSecurity = true;
					}
					
					if (node.getTextContent().equalsIgnoreCase("ssl") || node.getTextContent().equalsIgnoreCase("transportSecurity-1.0")) {
						transportSecurity = true;
					}
				}
				
			}
			
			if (!appSecurity) {
				validationErrors.add("Feature: appSecurity-2.0 not found.");
			}
			
			if (!transportSecurity) {
				validationErrors.add("Feature: transportSecurity-1.0 or ssl-1.0 not found.");
			}
				
			// Check the Endpoints to make sure they are compliant
			// uses Bean Validation
			// uses a loop since there could be more than 1 entry
			nodeList = doc.getElementsByTagName("httpEndpoint");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
					
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					EndpointInfo endpointInfo = new EndpointInfo();
					
					endpointInfo.setHost(element.getAttribute("host"));
					endpointInfo.setName(element.getAttribute("id"));
					endpointInfo.setHttpPort(Integer.parseInt(element.getAttribute("httpPort")));
					endpointInfo.setHttpsPort(Integer.parseInt(element.getAttribute("httpsPort")));
					
					Set<ConstraintViolation<EndpointInfo>> violations = validator.validate(endpointInfo);
					
					// Validate using Bean Validation and add violations to List
					if (!violations.isEmpty()) {						
						for (ConstraintViolation<EndpointInfo> violation : violations) {
							validationErrors.add("Endpoint: " + violation.getMessage());
						}
					}
				}
			}
			
			// Check the SSL entries
			// uses Bean Validation
			// uses a loop since there could be more than 1 entry
			nodeList = doc.getElementsByTagName("ssl");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
					
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					SSLInfo sslInfo = new SSLInfo();
						
					sslInfo.setId(element.getAttribute("id"));
					sslInfo.setProtocol(element.getAttribute("sslProtocol"));
					sslInfo.setSecurityLevel(element.getAttribute("securityLevel"));
					
					if (element.getAttribute("clientAuthenticationSupported").equalsIgnoreCase("true")) {
						sslInfo.setClientAuthSupported(true);
					} else {
						sslInfo.setClientAuthSupported(false);
					}

					Set<ConstraintViolation<SSLInfo>> violations = validator.validate(sslInfo);
					
					// Validate using Bean Validation and add violations to List
					if (!violations.isEmpty()) {						
						for (ConstraintViolation<SSLInfo> violation : violations) {
							validationErrors.add("SSL: " + violation.getMessage());
						}
					}
				}
			}	
			
			// Finished, so let's print out the Validation errors
			if (validationErrors.isEmpty()) {
				System.out.println("Validation found no errors");
				System.exit(0);
			}
			
			for (String error : validationErrors) {
				System.out.println(error);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace(System.err);
		}
	}

}
