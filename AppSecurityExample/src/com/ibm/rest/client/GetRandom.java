package com.ibm.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import com.ibm.rest.services.ResponseMessage;

public class GetRandom {

	// Normally the userid/pw would be injected and be encoded
	private static final String password = "user1";
	private static final String userid = "user1";
	private static final String urlString = "https://localhost:9443";
	private static final String uri = "/AppSecurityExample/secure/services/Random/AllRoles";
	
	public static void main(String[] args) {
		System.out.println(callRESTBasic());
	}
	
	
	// using BASIC authentication
	// to use FORM authentication the user needs signon with j_security_check first
	public static String callRESTBasic() {
		try {
			URL url = new URL(urlString + uri);
			URLConnection urlConnection = url.openConnection();
			String authentication = userid + ":" + password;
			String authenticationEncode = new String(Base64.getEncoder().encode(authentication.getBytes()));
			urlConnection.setRequestProperty("Authorization", "Basic " + authenticationEncode);
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
	        StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
	
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
	
	public String callRESTClient() {
		try {
			String authentication = userid + ":" + password;
			String authenticationEncode = "Basic " + DatatypeConverter.printBase64Binary(authentication.getBytes("UTF-8"));

			Client client = ClientBuilder.newClient();
			ResponseMessage message = client.target(urlString).path(uri)
						.request(MediaType.APPLICATION_JSON)
						.header("Authorization", authenticationEncode)
						.get(ResponseMessage.class);			
			return Integer.toString(message.getNumber());
	
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
 }
