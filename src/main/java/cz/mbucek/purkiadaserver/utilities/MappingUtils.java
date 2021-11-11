package cz.mbucek.purkiadaserver.utilities;

import org.springframework.http.converter.json.MappingJacksonValue;

import cz.mbucek.purkiadaserver.entities.User;
import cz.mbucek.purkiadaserver.utilities.View.Extended;
import cz.mbucek.purkiadaserver.utilities.View.Public;

public class MappingUtils {
	public static MappingJacksonValue toJacksonValue(Object object, Class<?> type) {
		MappingJacksonValue value = new MappingJacksonValue(object);
		value.setSerializationView(type);
		return value;
	}
	
	public static MappingJacksonValue basicMappingForUser(Object object, User user) {
		if(user != null && user.hasRole("admin")){
			return toJacksonValue(object, Extended.class);
		}
		return toJacksonValue(object, Public.class);
	}
	
}
