package cz.mbucek.purkiadaserver.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.mbucek.purkiadaserver.utilities.ApiInfo;

@RestController
@RequestMapping(path = "")
public class MainController {
	
	@Autowired
	private ApiInfo apiInfo;
	
	@GetMapping
	public ApiInfo getInfo(){
		return apiInfo;
	}
}
