package com.epam.testapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * EncodingFilter --- a class filter which specifies character encoding for all
 * input fields in jsp pages
 * 
 * @author Marharyta_Amelyanchuk
 */
@WebFilter(value = "/*", initParams = { @WebInitParam(name = "encoding", value = "UTF-8") })
public class EncodingFilter implements Filter {
	/** character encoding value */
	private String encoding = "UTF-8";

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("encoding");
		if (encodingParam != null) {
			encoding = encodingParam;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
