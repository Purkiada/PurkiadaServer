package cz.mbucek.purkiadaserver.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
/**
 * Converts roles from a JWT token.
 * It maps both resource and realm roles.
 * 
 * @author Matěj Bucek
 *
 */
public class JwtRoleConverter implements Converter<Jwt, AbstractAuthenticationToken>{

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		var all = getRoles(jwt.getClaims());
		Collection<GrantedAuthority> roles = all.stream().map(x -> new SimpleGrantedAuthority("ROLE_" + x)).collect(Collectors.toSet());

		return new JwtAuthenticationToken(jwt, roles);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getRoles(Map<String, Object> claims){
		//Realm roles are like this: ROLE_rolename
		//ROLE_admin
		final Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
		final Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
		List<String> all = new ArrayList<String>();

		List <String> l = (List<String>)realmAccess.get("roles");
		all.addAll(l);


		//Maps resource roles like this: ROLE_resourcename_rolename 
		//ROLE_account_manage_account
		Set<String> resources = resourceAccess.keySet();
		resources.forEach(res -> {
			Map<String, Object> r = (Map<String, Object>)resourceAccess.get(res);
			List<String> a = (List<String>)r.get("roles");
			a.forEach(n -> {
				all.add((res+"-"+n).replace("-", "_"));
			});
		});
		return all;
	}
}
