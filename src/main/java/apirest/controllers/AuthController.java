package apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import apirest.database.postgres.PostgresJPA;
import apirest.models.postgres.UserModel;
import apirest.util.Bcrypt;
import apirest.util.JsonWebToken;

@RestController
public class AuthController {
	
	@Autowired
	PostgresJPA postgres;
	
	@Autowired
	Bcrypt bcrypt;
	
	@Autowired
	JsonWebToken jwt;

	@PostMapping("/token")
	public String token(@RequestBody UserModel user) {
		
		if(user.getUsername() == null) return "username is required";
		
		if(user.getPassword() == null) return "password is required";
		
		try {
			
			UserModel userDB = postgres.findByUsername(user.getUsername());
		
			if(!bcrypt.compare(user.getPassword(), userDB.getPassword())) return "username or password incorrect";
			
			return jwt.createJwt(Long.toString(user.getId()), "Company", user.getUsername(), 43200000); // 43200000 milisegundos = 12 horas
		
		} catch (Exception e) {
			
			return "username or password incorrect";
			
		}
	}
}
