package cz.mbucek.purkiadaserver.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class MainController {
	
	@GetMapping
	public Map<String, Object> getInfo(){
		return null;
	}
}
