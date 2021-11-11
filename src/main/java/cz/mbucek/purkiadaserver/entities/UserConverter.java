package cz.mbucek.purkiadaserver.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
