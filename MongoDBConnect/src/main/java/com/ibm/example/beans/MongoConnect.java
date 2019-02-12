package com.ibm.example.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.ClientSession;

@ManagedBean
@SessionScoped
public class MongoConnect implements Serializable{
	private static final long serialVersionUID = 2247173195385179316L;

	private String userid;
	private String password;
	private String database;
	private String server;
	private int port;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public void connectDB() {
		ServerAddress address = new ServerAddress(this.getServer(), this.getPort());
		List<MongoCredential> credentials = new ArrayList<>();
		
		try {
			MongoClientOptions.Builder builder = MongoClientOptions.builder();
			builder.sslEnabled(true).build();
			MongoClientOptions sslOptions = builder.build();
			MongoCredential credential = MongoCredential.createMongoCRCredential(this.getUserid(), this.getDatabase(), this.getPassword().toCharArray());
			credentials.add(credential);
			MongoClient client = new MongoClient(address, credentials, sslOptions);
			ClientSession session = client.startSession();
			session.close();
			client.close();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Connection successful!"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Connection not successful: " + e.getMessage()));
		}
	}
}
