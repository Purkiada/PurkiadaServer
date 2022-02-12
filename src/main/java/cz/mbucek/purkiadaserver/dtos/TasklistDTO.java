package cz.mbucek.purkiadaserver.dtos;

import javax.validation.constraints.NotNull;

public record TasklistDTO(
			@NotNull String name
		) {

}
