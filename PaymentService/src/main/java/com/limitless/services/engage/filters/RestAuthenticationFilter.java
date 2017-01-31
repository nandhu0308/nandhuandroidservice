package com.limitless.services.engage.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.limitless.services.payment.PaymentService.util.AuthenticationUtil;

public class RestAuthenticationFilter implements Filter {
	
	final static Logger logger = Logger.getLogger(RestAuthenticationFilter.class);
			
	public static final String AUTHENTICATION_HEADER = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
			
			logger.info("Path: " + path);
			
			boolean authenticationStatus = false;
			
			//Get Version
			if(path.contains("getVersion") || path.contains("selfinvite")){
				authenticationStatus = true;
			} /*else if(path.endsWith("customer") && httpRequest.getMethod().equals("POST") ){
				authenticationStatus = AuthenticationUtil.getInstance().validateCredentials(authCredentials, true);
			} */else if(path.contains("login") || path.endsWith("split") || path.endsWith("credit") 
					|| path.endsWith("payment/trans") || path.contains("customer/register") || path.contains("deviceidbg") || path.contains("seller/register")) {
				authenticationStatus = AuthenticationUtil.getInstance().validateCredentials(authCredentials, true);
			}
			else{
				authenticationStatus = AuthenticationUtil.getInstance().authenticateUser(authCredentials);
			}

			if (authenticationStatus) {
				filter.doFilter(request, response);
			} else {
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing / Wrong Authentication Credentials");
				}
			}
			
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}