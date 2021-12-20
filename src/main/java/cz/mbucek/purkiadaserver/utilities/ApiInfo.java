package cz.mbucek.purkiadaserver.utilities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "api-info")
@ConstructorBinding
public record ApiInfo(
			String message,
			String version,
			String docs
		) {

}
