package com.ace.web.pf.config;

import java.io.IOException;
import java.sql.Driver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean{

	private String getToken(ServletRequest request)
	{
		String token=null;
		Cookie ck[] = ((HttpServletRequest) request).getCookies();
		if (ck != null) {
			for (int i = 0; i < ck.length; i++) {
				if (ck[i].getName().equalsIgnoreCase("ArmorTicket")) {
					token = ck[i].getValue();
					System.out.println(token);
					break;
				}
			}
		}
		return token;	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		chain.doFilter(request, response);
		
		
	}


}
