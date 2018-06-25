package com.ibm.security.login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.ibm.websphere.security.auth.WSSubject;
import com.ibm.websphere.security.auth.callback.WSCallbackHandlerImpl;
import com.ibm.websphere.security.auth.callback.WSRealmNameCallbackImpl;
import com.ibm.wsspi.security.auth.callback.WSServletRequestCallback;
import com.ibm.wsspi.security.token.AttributeNameConstants;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL IBM CORPORATION BE LIABLE FOR ANY CLAIM, DAMAGES 
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * This is a sample class to implement a LoginModule for JAAS.
 * @author Brian S Paskin (IBM Corporation)
 * @version 1.0 (21/08/2015)
 */

public class TwoFactorAuth implements LoginModule {
	private static final Logger logger = Logger.getLogger(TwoFactorAuth.class.getName());
	  
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    
	@Override
	public boolean abort() throws LoginException {
		logger.entering("TwoFactorAuth", "abort()");
		logger.exiting("TwoFactorAuth", "abort()");
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		logger.entering("TwoFactorAuth", "commit()");
		logger.exiting("TwoFactorAuth", "commit()");
		return true;
	} 

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {

		logger.entering("TwoFactorAuth", "initialize()");
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
		logger.exiting("TwoFactorAuth", "initialize()");
	}

	@Override
	public boolean login() throws LoginException {
		
		logger.entering("TwoFactorAuth", "login()");
		NameCallback nameCallback = null;
	    PasswordCallback passwordCallback = null;
	    WSServletRequestCallback wsServletRequestCallback = null;
	    WSRealmNameCallbackImpl wsRealmNameCallback = null;
	    		
	    Callback[] callbacks = new Callback[4];
	    callbacks[0] = nameCallback = new NameCallback("Username: ");
	    callbacks[1] = passwordCallback = new PasswordCallback("Password: ", false);
	    callbacks[2] = wsServletRequestCallback = new WSServletRequestCallback("HttpServletRequest: ");
	    callbacks[3] = wsRealmNameCallback = new WSRealmNameCallbackImpl("Realm name:");
	    
	    try {
			logger.log(Level.FINER, "login() callbackHandler");		
	        callbackHandler.handle(callbacks);
	      } catch (Exception e) {
	    	  logger.log(Level.SEVERE, e.getMessage(), e);
	    	  throw new LoginException(e.getMessage());
	      }
	    
	    String username = null;
	    String loginCode = null;
	    String password = null;
	    String realmName = null;
	    
	    if (null != nameCallback) {
	    	username = nameCallback.getName();
	    }
	    
	    if (null != passwordCallback) {
	    	password = String.valueOf(passwordCallback.getPassword());
	    }

	    if (null != wsServletRequestCallback) {
	    	loginCode = wsServletRequestCallback.getHttpServletRequest().getParameter("j_code");
	    }
	    
	    if (null != wsRealmNameCallback) {
	    	realmName = wsRealmNameCallback.getRealmName();
	    }
	    
	    if (null == loginCode || null == password || null == username || null == realmName) {
			logger.log(Level.FINER, "login() item is null");	
			logger.exiting("TwoFactorAuth", "login()");
	    	return false;
	    }
	    
	    // Check the code first
	    Authenticator auth = new Authenticator();
	    
	    if (!auth.checkUserCode(username, Long.parseLong(loginCode))) {
			logger.log(Level.FINER, "login() code check is incorrect");		
			logger.exiting("TwoFactorAuth", "login()");
	    	return false;
	    }
	    
		logger.log(Level.FINER, "login() code check matches");		

	    // try to logon
	    try {
	    	// 
	    	// if using WAS/Liberty user registry (i.e. LDAP, File).
	    	//
		    //LoginContext loginContext = new LoginContext("WSLogin",
		    //		new WSCallbackHandlerImpl(username, realmName, password));
		    //loginContext.login();
		    
	    	//
	    	// Use these for Database access only
	    	//
	    	if (!auth.checkUserLogin(username, password)) {
	    		logger.log(Level.FINER, "login() passwords did not match");
	    		logger.exiting("TwoFactorAuth", "login()");
	    		return false;
	    	}
	    	
	    	// get group 
	    	String role = auth.getRoles(username);
	    	
	    	Hashtable<String, Object> hashtable = (Hashtable<String, Object>) sharedState.get(AttributeNameConstants.WSCREDENTIAL_PROPERTIES_KEY);
	    	if (null == hashtable ) {
	    		hashtable = new java.util.Hashtable<String, Object>();
	    	}
	    	List<String> groups = new ArrayList<String>();
	    	groups.add(role);
	    	hashtable.put(AttributeNameConstants.WSCREDENTIAL_UNIQUEID,  username);
	        hashtable.put(AttributeNameConstants.WSCREDENTIAL_SECURITYNAME,  username);
	        hashtable.put(AttributeNameConstants.WSCREDENTIAL_GROUPS, groups);
	        hashtable.put(AttributeNameConstants.WSCREDENTIAL_CACHE_KEY,"CacheKey:" + username );     
	        
			Map<String, Hashtable<String, ?>> mySharedState = (Map<String, Hashtable<String, ?>>) sharedState;
	        mySharedState.put(AttributeNameConstants.WSCREDENTIAL_PROPERTIES_KEY,hashtable);        			

			logger.log(Level.FINER, "login() logged in");		
	    } catch (LoginException e) {
	    	logger.log(Level.SEVERE, e.getMessage(), e);
			logger.exiting("TwoFactorAuth", "login()");
	    	return false;
	    }
		logger.exiting("TwoFactorAuth", "login()");
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		logger.entering("TwoFactorAuth", "logout()");
		logger.exiting("TwoFactorAuth", "logout()");
		return true;
	}
}
