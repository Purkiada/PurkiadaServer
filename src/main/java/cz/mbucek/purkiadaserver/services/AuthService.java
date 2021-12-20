package cz.mbucek.purkiadaserver.services;

import org.keycloak.representations.idm.UserRepresentation;

public interface AuthService {
	public UserRepresentation getUserById(String id);
}
