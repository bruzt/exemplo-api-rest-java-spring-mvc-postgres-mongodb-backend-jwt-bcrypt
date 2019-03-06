package apirest.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import apirest.database.postgres.PostgresJPA;
import apirest.models.postgres.UserModel;
import apirest.util.Bcrypt;
import apirest.util.VerifyBearerToken;

@RestController
//@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	PostgresJPA postgres;
	
	@Autowired
	Bcrypt bcrypt;
	
	@Autowired
	VerifyBearerToken bearerToken;
	
	/**
	 * 
	 * GET - Retorna todos os usuarios
	 * 
	 */
	
	@GetMapping("/user")
	public ResponseEntity<List<UserModel>> readPeople
	(
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<List<UserModel>>(HttpStatus.BAD_REQUEST);
		
		List<UserModel> result = postgres.findAll();
		
		// Não mostra a senha
		for(int i=0; i < result.size(); i++) {
			result.get(i).setPassword("SECRET");
		}
		
		return new ResponseEntity<List<UserModel>>(result, HttpStatus.OK);
	}
	
	/**
	 * 
	 * GET - Retorna pelo ID
	 * 
	 */
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Optional<UserModel>> readPeopleById
	(
			@PathVariable long id,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<Optional<UserModel>>(HttpStatus.BAD_REQUEST);
		
		Optional<UserModel> user = postgres.findById(id);
		
		user.get().setPassword("SECRET");
		
		return new ResponseEntity<Optional<UserModel>>(user, HttpStatus.OK);
	}
	
	/**
	 * 
	 * POST - Adiciona um novo usuario
	 * 
	 */
	
	@PostMapping("/user")
	public ResponseEntity<UserModel> createPeople
	(
			@Valid @RequestBody UserModel newUser,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<UserModel>(HttpStatus.BAD_REQUEST);
		
		newUser.setPassword(bcrypt.hash(newUser.getPassword()));
		
		UserModel user = postgres.save(newUser);
		
		user.setPassword("SECRET");
		
		return new ResponseEntity<UserModel>(user, HttpStatus.OK);
	}
	
	/**
	 * 
	 * PUT - Atualiza usuario pelo ID
	 * 
	 */
	
	@PutMapping("/user/{id}")
	public ResponseEntity<UserModel> updatePeople
	(
			@PathVariable long id, 
			@Valid @RequestBody UserModel userUpdate,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<UserModel>(HttpStatus.BAD_REQUEST);
		
		Optional<UserModel> result = postgres.findById(id);
		result.get().setUsername(userUpdate.getUsername());
		result.get().setPassword(bcrypt.hash(userUpdate.getPassword()));
		
		UserModel user = postgres.saveAndFlush(result.get());
		user.setPassword("SECRET");
		
		return new ResponseEntity<UserModel>(user, HttpStatus.OK);
	}
	
	/**
	 * 
	 * DELETE - Deleta usuario pelo ID
	 * 
	 */
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Boolean> deletePeople
	(
			@PathVariable long id,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		
		try {
			
			postgres.deleteById(id);
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}catch (Exception e){
			
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
