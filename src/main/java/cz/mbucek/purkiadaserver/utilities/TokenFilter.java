package cz.mbucek.purkiadaserver.utilities;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import cz.mbucek.purkiadaserver.services.UserService;

@Component
public class TokenFilter implements Filter{

	@Autowired
	private UserService userService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() instanceof Jwt) {
			Jwt jwt = (Jwt)auth.getPrincipal();
			
			userService.updateUser(jwt);
		}
		chain.doFilter(request, response);
	}

}
