package com.ibm.example.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;

/**
 * Search Google with the String sent and return a list of Results.  Have to make
 * the request look like a googlebot for it to work correctly.  Limiting the 
 * number of returned reseults to 10.
 * 
 * @author: Brian S Paskin (IBM Italia)
 * @version: 1.0.0.0 (2019/05/15)
 * 
 **/
@RequestScoped
public class Search {
	
	private final String agent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	public List<Result> findResults(String term) {
    	logger.entering(this.getClass().getCanonicalName(), "findResults");
    	
    	String query = "https://www.google.com/search?q=" + term + "&num=10";
    	StringBuilder sb = new StringBuilder();
    	LocalTime now =  LocalTime.now();
    	
    	try { 
    		
    		// Open a URL Connection using the googlebot agent and send the 
    		// request 
	    	URL url = new URL(query);
	    	URLConnection connection = url.openConnection();
	    	connection.setRequestProperty("User-Agent", agent);
	    	InputStream is = connection.getInputStream();
	    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    	
	    	// get all the results back and append it the variable line
	    	String line;
	    	while ((line = br.readLine()) != null) {
	    		sb.append(line);
	    	}
	    	
	    	if (br != null) {
	    		br.close();
	    	}
    	} catch (IOException e) {
    		logger.log(Level.SEVERE, e.getMessage(), e);
    		return null;
    	}
    	
    	// Using regex go through and match the pattern, which is the pattern for the
    	// URLs that are displayed on the web page, and then sacve them to Result.  Return
    	// A list of results.
        List<Result> results = new ArrayList<>();
        String pattern1 = "<h3 class=\"r\"><a href=\"/url?q=";
        String pattern2 = "\">";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(sb);

        while (m.find()) {
            String domainName = m.group(0).trim();
            domainName = domainName.substring(domainName.indexOf("/url?q=") + 7);
            domainName = domainName.substring(0, domainName.indexOf("&amp;"));

            Result result = new Result();
            result.setSearchString(term);
            result.setUrl(domainName);
            result.setTimesSeached(now);
            results.add(result);
        }
        
    	logger.exiting(this.getClass().getCanonicalName(), "findResults");	
        return results;
	}
}
