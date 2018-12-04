from javax.servlet.http import HttpServlet 

class helloworld(HttpServlet): 
	def doGet(self, request, response):
		response.setContentType("text/html")
		out = response.getWriter()
		out.println("Hello from the world of Jython Servlets")
