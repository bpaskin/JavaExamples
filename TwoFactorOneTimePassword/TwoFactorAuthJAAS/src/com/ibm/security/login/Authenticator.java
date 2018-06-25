package com.ibm.security.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;

import org.apache.commons.codec.binary.Base32;


/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL IBM CORPORATION BE LIABLE FOR ANY CLAIM, DAMAGES 
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * This is a sample class to implement a the Authenticator for Google Authenticator and
 * check and create users.
 * 
 * Based on code from https://code.google.com/p/vellum/wiki/GoogleAuthenticator
 * Requires Apache Commons Codec for Base32 (http://commons.apache.org/)
 * Key info : https://github.com/google/google-authenticator/wiki/Key-Uri-Format
 * 
 * @author Brian S Paskin (IBM Corporation)
 * @version 1.0 (21/08/2015)
 */
public class Authenticator {
	private static final Logger logger = Logger.getLogger(Authenticator.class.getName());
	public static final String googleURL = "http://chart.apis.google.com/chart?cht=qr&chs=200x200&chl=";
	private static SecureRandom random = new SecureRandom();
	
	public static long getTimeIndex() {
		logger.entering("Authenticator", "getTimeIndex()");
		logger.exiting("Authenticator", "getTimeIndex()");
	    return System.currentTimeMillis() / 1000 / 30;
	}

	public String createSecret() {
		logger.entering("Authenticator", "createSecret()");
		byte[] buffer = new byte[10];
		random.nextBytes(buffer);
		String secret = new String(new Base32().encode(buffer));
		logger.log(Level.FINEST, "Secret : " + secret);
		logger.exiting("Authenticator", "createSecret()");
		return secret;
	}
	
	public BufferedImage generateQRURL (String issuer, String user, String secret) throws IOException {
		logger.entering("Authenticator", "generateQRURL()");

		String TOTPParams = "otpauth://totp/" + issuer + ":" + user + "?secret=" + secret + "&issuer=" + issuer;
		logger.log(Level.FINEST, "URL : " + googleURL + TOTPParams);
		
		BufferedImage image = null;
		try {
			logger.log(Level.FINEST, "Calling URL");
			URL url = new URL(googleURL + TOTPParams);
			logger.log(Level.FINEST, "Retrieving image");
			image = ImageIO.read(url);
		} catch (IOException e) {
			logger.exiting("Authenticator", "generateQRURL()");
			throw new IOException("Error retrieving image from Google : " + e.getMessage());
		}
		logger.exiting("Authenticator", "generateQRURL()");
		return image;
	}
	
	public boolean checkUserLogin(String user, String password) throws LoginException {
		logger.entering("Authenticator", "checkUser()");
		String storedPW = new AccessDAO().getPassword(user);
		logger.log(Level.FINEST, "Stored PW : Entered PW = " + storedPW + " : " + password);
		logger.exiting("Authenticator", "checkUser()");
		return (storedPW.equals(password));
	}
	
	public boolean checkUserCode(String user, long enteredCode) throws LoginException {
		logger.entering("Authenticator", "checkUser()");
		long calculatedCode = 0;
		
		try {
			String secret = new AccessDAO().getSecret(user);
			byte[] secretBytes = new Base32().decode(secret);

			SecretKeySpec signKey = new SecretKeySpec(secretBytes, "HmacSHA1");
			ByteBuffer buffer = ByteBuffer.allocate(8);
			buffer.putLong(getTimeIndex());
			byte[] timeBytes = buffer.array();
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signKey);
			byte[] hash = mac.doFinal(timeBytes);
			int offset = hash[19] & 0xf;
			long truncatedHash = hash[offset] & 0x7f;
			for (int i = 1; i < 4; i++) {
				truncatedHash <<= 8;
				truncatedHash |= hash[offset + i] & 0xff;
			}
			
			calculatedCode = truncatedHash %= 1000000;

		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new LoginException(e.getMessage());
		}
		
		logger.log(Level.FINEST, "Entered Code : Calculated Code = " + enteredCode + " : " + calculatedCode);
		logger.exiting("Authenticator", "checkUser()");
		return (enteredCode == calculatedCode);
	}
	
	public void createUser(String user, String password, String secret) throws LoginException {
		logger.entering("Authenticator", "createUser()");
		new AccessDAO().createUser(user, password, secret);
		logger.exiting("Authenticator", "createUser()");
	}
	
	public String getRoles(String user) throws LoginException {
		logger.entering("Authenticator", "getRole()");
		String role = new AccessDAO().getRoles(user);
		logger.exiting("Authenticator", "getRole()");
		return role;
	}
}
