package cz.mbucek.purkiadaserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import cz.mbucek.purkiadaserver.entities.User;

@Service
public class UserService {
	@Bean
	@SessionScope
	public User newUser() {
		return new User();
	}
	
	@Autowired
	private User user;

	public void updateUser(Jwt jwt) {
		this.user.setClaims(jwt.getClaims());
	}
}
