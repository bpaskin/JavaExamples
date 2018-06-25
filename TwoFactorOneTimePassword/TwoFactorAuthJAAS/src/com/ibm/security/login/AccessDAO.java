package com.ibm.security.login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL IBM CORPORATION BE LIABLE FOR ANY CLAIM, DAMAGES 
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * This is a sample class to implement database access.
 * @author Brian S Paskin (IBM Corporation)
 * @version 1.0 (21/08/2015)
 */

public class AccessDAO {
	private static final Logger logger = Logger.getLogger(AccessDAO.class.getName());
	private static final String jndiDB = "jdbc/TOTP";
	private static final String tableName = "TOTP";
	private static final String tableNameUser = "users";
	private static final String tableNameRole = "roles";
	private static final String key = "6eXXNK-R2=twxYW9+6jB";
	private static final String keyUser = "@A1ht{B6,:SH@wGxso{&";

	protected void createUser(String user, String password, String secret) throws LoginException {
		logger.entering("AccessDAO", "createUser()");
		String query = "insert into " + tableNameUser + " (user, password) values ('" + user + "', AES_ENCRYPT('" + password + "','" + keyUser + "'))";		
		Connection conn = null;
		
		try { 
			InitialContext ic = new InitialContext();
			logger.log(Level.FINEST, "Acquire DataSource : " + jndiDB);
			DataSource ds = (DataSource) ic.lookup(jndiDB);
			logger.log(Level.FINEST, "Acquire Connection");
			conn = ds.getConnection();
			logger.log(Level.FINEST, "Create Statement");
			Statement stmt = conn.createStatement();
			logger.log(Level.FINEST, "Execute Query : " + query);
			stmt.execute(query);
			query = "insert into " + tableName + " (user, secret) values ('" + user + "', AES_ENCRYPT('" + secret + "','" + key + "'))";		
			logger.log(Level.FINEST, "Execute Query : " + query);
			stmt.execute(query);
			query = "insert into " + tableNameRole + " (user, role) values ('" + user + "', 'admins')";		
			logger.log(Level.FINEST, "Execute Query : " + query);
			stmt.execute(query);
		
			stmt.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace(System.err);
			logger.exiting("AccessDAO", "createUser()");
			throw new LoginException(e.getMessage());
		} finally {
			try {
				if (null != conn)  {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
			logger.exiting("AccessDAO", "createUser()");
		}
	}
	
	protected String getSecret(String user) throws LoginException {
		logger.entering("AccessDAO", "getSecret()");

		String query = "select AES_DECRYPT(secret,'" +  key + "') from " + tableName + " where user = '" + user +"'";
		Connection conn = null;
		String secret = null;
		
		try { 
			InitialContext ic = new InitialContext();
			logger.log(Level.FINEST, "Acquire DataSource : " + jndiDB);
			DataSource ds = (DataSource) ic.lookup(jndiDB);
			logger.log(Level.FINEST, "Acquire Connection");
			conn = ds.getConnection();
			logger.log(Level.FINEST, "Create Statement");
			Statement stmt = conn.createStatement();
			logger.log(Level.FINEST, "Execute Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				logger.log(Level.FINEST, "Acquire record data");
				secret = rs.getString("AES_DECRYPT(secret,'" +  key + "')");
			}
			
			rs.close();
			stmt.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace(System.err);
			logger.entering("AccessDAO", "getSecret()");
			throw new LoginException(e.getMessage());
		} finally {
			try {
				if (null != conn)  {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}
		logger.exiting("AccessDAO", "getSecret()");
		return secret;
	} 
	
	protected String getPassword(String user) throws LoginException {
		logger.entering("AccessDAO", "getPassword(String)");

		String query = "select AES_DECRYPT(password,'" +  keyUser + "') from " + tableNameUser + " where user = '" + user +"'";
		Connection conn = null;
		String secret = null;
		
		try { 
			InitialContext ic = new InitialContext();
			logger.log(Level.FINEST, "Acquire DataSource : " + jndiDB);
			DataSource ds = (DataSource) ic.lookup(jndiDB);
			logger.log(Level.FINEST, "Acquire Connection");
			conn = ds.getConnection();
			logger.log(Level.FINEST, "Create Statement");
			Statement stmt = conn.createStatement();
			logger.log(Level.FINEST, "Execute Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				logger.log(Level.FINEST, "Acquire record data");
				secret = rs.getString("AES_DECRYPT(password,'" +  keyUser + "')");
			}
			
			rs.close();
			stmt.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace(System.err);
			logger.exiting("AccessDAO", "getPassword(String)");
			throw new LoginException(e.getMessage());
		} finally {
			try {
				if (null != conn)  {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}
		logger.exiting("AccessDAO", "getPassword(String)");
		return secret;
	} 
	
	protected String getRoles(String user) throws LoginException {
		logger.entering("AccessDAO", "getRole()");

		String query = "select role from " + tableNameRole + " where user = '" + user +"'";
		Connection conn = null;
		String role = null;
		
		try { 
			InitialContext ic = new InitialContext();
			logger.log(Level.FINEST, "Acquire DataSource : " + jndiDB);
			DataSource ds = (DataSource) ic.lookup(jndiDB);
			logger.log(Level.FINEST, "Acquire Connection");
			conn = ds.getConnection();
			logger.log(Level.FINEST, "Create Statement");
			Statement stmt = conn.createStatement();
			logger.log(Level.FINEST, "Execute Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				logger.log(Level.FINEST, "Acquire record data");
				role = rs.getString("role");
			}
			
			rs.close();
			stmt.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace(System.err);
			logger.exiting("AccessDAO", "getRole()");
			throw new LoginException(e.getMessage());
		} finally {
			try {
				if (null != conn)  {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}
		logger.exiting("AccessDAO", "getRole()");
		return role;
	}
	
	public void createTables() throws LoginException {
		logger.entering("AccessDAO", "createTables()");
		Connection conn = null;

		try { 
			String query = "CREATE TABLE users (user VARCHAR(20) NOT NULL PRIMARY KEY, password VARBINARY(200) NOT NULL)";
			InitialContext ic = new InitialContext();
			logger.log(Level.FINEST, "Acquire DataSource : " + jndiDB);
			DataSource ds = (DataSource) ic.lookup(jndiDB);
			logger.log(Level.FINEST, "Acquire Connection");
			conn = ds.getConnection();
			logger.log(Level.FINEST, "Create Statement");
			Statement stmt = conn.createStatement();
			logger.log(Level.FINEST, "Execute Query : " + query);
			stmt.execute(query);

			query = "CREATE TABLE TOTP (user VARCHAR(20) NOT NULL, secret VARBINARY(200), FOREIGN KEY(user) REFERENCES users(user))";
			logger.log(Level.FINEST, "Execute Query : " + query);
			stmt.execute(query);

			query = "CREATE TABLE roles (user VARCHAR(20) NOT NULL, role VARCHAR(20) NOT NULL, FOREIGN KEY(user) REFERENCES users(user))";
			logger.log(Level.FINEST, "Execute Query : " + query);
			stmt.execute(query);
			
			stmt.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace(System.err);
			logger.exiting("AccessDAO", "getRole()");
			throw new LoginException(e.getMessage());
		} finally {
			try {
				if (null != conn)  {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}

		logger.exiting("AccessDAO", "createTables()");

	}


}
