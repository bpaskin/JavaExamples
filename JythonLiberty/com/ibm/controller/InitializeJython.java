package com.ibm.controller;

import javax.servlet.annotation.WebServlet;
import org.python.util.PyServlet;

@WebServlet(urlPatterns="*.py", loadOnStartup=1)
public class InitializeJython extends PyServlet {
	private static final long serialVersionUID = 2695363231945671860L;
}
