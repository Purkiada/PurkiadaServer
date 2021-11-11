package cz.mbucek.purkiadaserver.configs;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cz.mbucek.purkiadaserver.utilities.JwtRoleConverter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
			.cors()
		.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.PUT, "/v1/action")
					.hasAnyRole("admin")
				.antMatchers(HttpMethod.GET, "/v1/action/{actionId}/submit")
					.authenticated()
				.antMatchers(HttpMethod.PUT, "/v1/action/{actionId}/submit")
					.authenticated()
				.antMatchers(HttpMethod.DELETE, "/v1/action/{actionId}")
					.hasAnyRole("admin")
				.antMatchers(HttpMethod.PATCH, "/v1/action/{actionId}")
					.hasAnyRole("admin")
				.anyRequest()
					.permitAll()
		.and()
				.oauth2ResourceServer()
					.jwt()
						.jwtAuthenticationConverter(new JwtRoleConverter());
	}
}
