package com.ibm.utility;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.utility.Status.STATUS;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@RequestScoped
public class RetrieveFile {
	private Logger log = LoggerFactory.getLogger(RetrieveFile.class);
	
	@ConfigProperty(name = "remote.user")
	String USERNAME;
	
	@ConfigProperty(name = "remote.password")
	String PASSWORD;
	
	@ConfigProperty(name = "remote.host")
	String HOST;
	
	@ConfigProperty(name = "remote.port")
	int PORT;
	
	@ConfigProperty(name = "remote.filename")
	String FILENAME;
	
	@ConfigProperty(name = "tmp.directory", defaultValue = "/tmp")
	String TMPDIR;
	
	@Inject
	Status status;
	
	@Inject
	PasswordHelper security;
	
	public boolean retrieveFile() {
		log.debug("Entering retrieveFile()");	
		log.debug("user: " + USERNAME + "\nhost: " + HOST + "\nport: " + PORT + "\nfilename:" + FILENAME);
		
		try {
			if (null == USERNAME || null == PASSWORD || null == HOST || null == FILENAME || PORT == 0) {
				throw new Exception ("Necessary properties not set, check application.properties file");
			}
			
			PASSWORD = security.decrypt(PASSWORD);
			
			JSch jsch = new JSch();
			Session session = jsch.getSession(USERNAME, HOST, PORT);
			
			log.debug("Received SFTP Session");	
			
			session.setPassword(PASSWORD);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(5000);
			
			log.debug("Session connected");
			
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			
			log.debug("Channel connected");
			
			if (FILENAME.contains("/")) {
				channel.get(FILENAME, TMPDIR + "/" + FILENAME.substring(FILENAME.lastIndexOf("/") + 1));
			} else {
				channel.get(FILENAME, TMPDIR + "/" + FILENAME);
			}
			
			log.debug("Got file: " + FILENAME);

			channel.exit();
			
			log.debug("Channel exit");
			
			session.disconnect();
			
			log.debug("Session disconnect");
		} catch (SftpException e) {
			status.setStatus(STATUS.DOWN);
			log.error("Exception in SFTP : ", e);
			log.debug("Exiting retrieveFile()");
			return false;
		} catch (Exception e) {
			status.setStatus(STATUS.DOWN);
			log.error("Exception in SFTP\n", e);
			log.debug("Exiting retrieveFile()");
			return false;
		}		
		
		log.debug("Exiting retrieveFile()");
		return true;
	}
}
