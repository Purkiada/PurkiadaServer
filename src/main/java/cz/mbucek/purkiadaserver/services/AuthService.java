package cz.mbucek.purkiadaserver.services;

import cz.mbucek.purkiadaserver.dtos.PublicUserDTO;

public interface AuthService {
	public PublicUserDTO getUserById(String id);
}
