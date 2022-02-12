package cz.mbucek.purkiadaserver.dtos;

import com.fasterxml.jackson.annotation.JsonView;

import cz.mbucek.purkiadaserver.utilities.View.Public;

/**
 * Retrieved from OAuth server, Keycloak in our instance.
 * 
 * @author Matěj Bucek
 */
public record PublicUserDTO(
			/**
			 * The sub claim from the token.
			 **/
			@JsonView(Public.class) String id,
			@JsonView(Public.class) String username,
			@JsonView(Public.class) String email,
			@JsonView(Public.class) String firstname,
			@JsonView(Public.class) String lastname
		) {
	
}
