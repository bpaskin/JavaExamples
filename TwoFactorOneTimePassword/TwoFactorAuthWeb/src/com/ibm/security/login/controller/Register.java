package com.ibm.security.login.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.security.login.Authenticator;


/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL IBM CORPORATION BE LIABLE FOR ANY CLAIM, DAMAGES 
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * This is a sample class to register a user for TOTP.
 * @author Brian S Paskin (IBM Corporation)
 * @version 1.0 (21/08/2015)
 */

@WebServlet({ "/Register", "/register" })
public class Register extends HttpServlet {
	private static final long serialVersionUID = 2963040760387729424L;
	private static final String issuer = "DeveloperWorks";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the Session
		HttpSession session = request.getSession(true);
		session.removeAttribute("message");
		
		// check to see if the correct info was passed in the post
		if (null == request.getParameter("username")) {
			session.setAttribute("message", "USERNAME not entered");
			getServletContext().getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		if (request.getParameter("username").length() == 0) {
			session.setAttribute("message", "USERNAME not entered");
			getServletContext().getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		if (null == request.getParameter("password")) {
			session.setAttribute("message", "PASSWORD not entered");
			getServletContext().getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		if (request.getParameter("password").length() == 0) {
			session.setAttribute("message", "PASSWORD not entered");
			getServletContext().getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		String user = request.getParameter("username");
		String password = request.getParameter("password");

		Authenticator auth = new Authenticator();
		String secret = auth.createSecret();
		session.setAttribute("secret", secret);

		try {
			// first create the user to make sure that the user can be added to the DB
			auth.createUser(user, password, secret);
			
			session.setAttribute("user", user);
			
			// get the picture of the create QR Code
			BufferedImage image = auth.generateQRURL(issuer, user, secret);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( image, "jpg", baos );
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			baos.close();
			
			String imageb64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
			session.setAttribute("qrcode", imageb64);
		} catch (Exception e) {
			session.setAttribute("message", "Error creating user : " + e.getMessage());
			e.printStackTrace(System.err);
		}
		
		getServletContext().getRequestDispatcher("register.jsp").forward(request, response);
	}

}
