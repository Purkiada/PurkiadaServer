package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.util.Optional;

public class Asserts {
	public static void notNull(Object object, LocalizedRuntimeException ex) {
		if(object == null)
			throw ex;
	}
	
	public static void notEmpty(Optional<?> object, LocalizedRuntimeException ex) {
		if(object.isEmpty())
			throw ex;
	}
}
