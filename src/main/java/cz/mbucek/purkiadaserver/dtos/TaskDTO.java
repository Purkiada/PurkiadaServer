package cz.mbucek.purkiadaserver.dtos;

import javax.validation.constraints.NotNull;

public record TaskDTO(
		@NotNull String name
		) {

}
