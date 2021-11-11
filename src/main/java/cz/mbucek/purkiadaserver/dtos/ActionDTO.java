package cz.mbucek.purkiadaserver.dtos;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

public record ActionDTO(
						@NotNull String name, 
						@NotNull String subName, 
						@NotNull String description, 
						@NotNull ZonedDateTime registrationStart, 
						@NotNull ZonedDateTime registrationEnd, 
						@NotNull ZonedDateTime actionStart, 
						@NotNull ZonedDateTime actionEnd, 
						@NotNull Integer maxUsers, 
						@NotNull Boolean hidden
						) {

}
