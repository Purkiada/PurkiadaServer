package cz.mbucek.purkiadaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cz.mbucek.purkiadaserver.utilities.ApiInfo;

@SpringBootApplication
@EnableConfigurationProperties(value = {ApiInfo.class})
public class PurkiadaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurkiadaServerApplication.class, args);
	}

}
