package com.ibm.pmi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.pmi.bean.ResourceBean;
import com.ibm.pmi.bean.Serverbean;
import com.ibm.websphere.cache.DistributedObjectCache;

@WebServlet({ "/ShowStats", "/" })
public class ShowStats extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String DOC_NAME = "cache/PMIStats";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println("<h1>PMI Statistics</h1>");
		
		try {
			InitialContext ic = new InitialContext();
			DistributedObjectCache doc = (DistributedObjectCache) ic.lookup(DOC_NAME);
			
			@SuppressWarnings("unchecked")
			Collection<Serverbean> servers = doc.values();
			for (Serverbean server : servers) {
				out.println("<h2>" + server.getServerName() + " - " + server.getNodeName() + "</h2>");

				for (ResourceBean resource : server.getResourceBean()) {
					Date date = new Date(resource.getTimestamp());
					out.println("<h3>" + resource.getResourceName() + " " + date + "</h3>");
					out.println("Description:" + resource.getDescription() + "<br>");
					out.println("Count:" + resource.getCount() + "<br>");
					out.println("Current Mark:" + resource.getCurrentMark() + "<br>");
					out.println("High Water Mark:" + resource.getHighWaterMark() + "<br>");
					out.println("Low Water Mark:" + resource.getLowWaterMark() + "<br>");
					out.println("Min Time:" + resource.getMinTime() + "<br>");
					out.println("Max Time:" + resource.getMaxTime() + "<br>");
					out.println("Total Time:" + resource.getTotalTime() + "<br>");
					out.println("<br>");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace(out);
		}
		
	}

}
