package com.ibm.example;

import java.security.SecureRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomService {

	private static SecureRandom random = new SecureRandom();
	
	@GetMapping("/random")
	public int chooseRandom() {
		return random.nextInt();
	}
}
