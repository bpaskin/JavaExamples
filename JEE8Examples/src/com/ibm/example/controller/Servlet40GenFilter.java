package com.ibm.example.controller;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(
		dispatcherTypes = {DispatcherType.REQUEST }, 
		urlPatterns = { "/Servlet40", "/servlet40"}
)
public class Servlet40GenFilter extends GenericFilter implements Filter {
	private static final long serialVersionUID = 7948387723427238893L;

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Executing " + getFilterName());
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		fConfig.getServletContext().setSessionTimeout(15);
		System.out.println("Generic Web Filter Initialized");
	}
	
	@Override
	public String getFilterName() {
		return "PizzaFilter";
	}
}
