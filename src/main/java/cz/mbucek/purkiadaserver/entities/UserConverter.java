package cz.mbucek.purkiadaserver.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts {@link User} to its ID and conversely.
 * This is mainly used when some database entity is about to save or be retrieved.
 * 
 * @author Matěj Bucek
 *
 */
@Converter
public class UserConverter implements AttributeConverter<User, String> {

	@Override
	public String convertToDatabaseColumn(User attribute) {
		return attribute.getUserId();
	}

	@Override
	public User convertToEntityAttribute(String dbData) {
		return new User(dbData);
	}

}
