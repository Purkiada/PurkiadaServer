package cz.mbucek.purkiadaserver.services;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cz.mbucek.purkiadaserver.dtos.PublicUserDTO;

@Service
public class KeycloakAuthService implements AuthService{
	
	@Value("${keycloak.server-url}")
	private String serverUrl;
	
	@Value("${keycloak.realm}")
	private String realm;
	
	@Value("${keycloak.client-id}")
	private String clientId;
	
	@Value("${keycloak.client-secret}")
	private String clientSecret;
	
	@Bean
	public Keycloak buildKeycloak() {
		return KeycloakBuilder.builder()
								.serverUrl(serverUrl)
								.realm(realm)
								.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
								.clientId(clientId)
								.clientSecret(clientSecret)
								.build();
	}
	
	@Autowired
	private Keycloak keycloak;

	@Override
	public PublicUserDTO getUserById(String id) {
		return toPublicUser(keycloak.realm(realm).users().get(id).toRepresentation());
	}
	
	private PublicUserDTO toPublicUser(UserRepresentation user) {
		try {
			return new PublicUserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
}
