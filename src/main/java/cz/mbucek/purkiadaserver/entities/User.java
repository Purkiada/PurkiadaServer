package cz.mbucek.purkiadaserver.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.jwt.JwtClaimNames;

import cz.mbucek.purkiadaserver.utilities.JwtRoleConverter;
/**
 * This object makes it easier to work with Users.
 * For instance, we can get this representation from JWT token an then assign this user
 * to an Action and it will automatically convert to user's ID using {@link UserConverter}.
 * 
 * @author Matěj Bucek
 *
 */
public class User {
	private Map<String, Object> claims;
	
	public User() {
		this.claims = new HashMap<String, Object>();
		var realmAccess = new HashMap<String, Object>();
		realmAccess.put("roles", new ArrayList<String>());
		this.claims.put("realm_access", realmAccess);
		var resourceAccess = new HashMap<String, Object>();
		this.claims.put("resource_access", resourceAccess);
	}
	
	public User(String userId) {
		this();
		this.claims.put(JwtClaimNames.SUB, userId);
	}
	
	public User(Map<String, Object> claims) {
		this.claims = claims;
	}
	
	public void setClaims(Map<String, Object> claims) {
		this.claims = claims;
	}
	
	public Map<String, Object> getClaims(){
		return claims;
	}
	
	public String getUserId() 
	{
		return getClaimAsString(JwtClaimNames.SUB);
	}
	
	public String getClaimAsString(String claim) {
		return (String) this.claims.get(claim);
	}
	
	public boolean hasRole(String role) {
		if(claims == null)
			return false;
		var roles = JwtRoleConverter.getRoles(claims);
		return roles.contains(role.replace("ROLE_", ""));
	}
}
