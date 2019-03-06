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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apirest.database.mongodb.MongoRepo;
import apirest.models.mongodb.PeopleModel;
import apirest.util.JsonWebToken;
import apirest.util.VerifyBearerToken;

@RestController
public class PeopleController {

	@Autowired
	MongoRepo mongodb;
	
	@Autowired
	JsonWebToken jwt;
	
	@Autowired
	VerifyBearerToken bearerToken;
	
	
	/**
	 * 
	 * GET - Retorna tudo
	 * 
	 */
	
	@GetMapping("/people")
	public ResponseEntity<List<PeopleModel>> readAll
	(
			@RequestParam(value="limit", defaultValue="10") int limit,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	)
	{
		
		if(!bearerToken.verify(token)) return new ResponseEntity<List<PeopleModel>>(HttpStatus.BAD_REQUEST);
		
		List<PeopleModel> users = mongodb.findAll();
		
		return new ResponseEntity<List<PeopleModel>>(users, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * GET - Retorna pelo ID
	 * 
	 */
	
	@GetMapping("/people/{id}")
	public ResponseEntity<Optional<PeopleModel>> readById
	(
			@PathVariable String id,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	)
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<Optional<PeopleModel>>(HttpStatus.BAD_REQUEST);
		
		Optional<PeopleModel> user = mongodb.findById(id);
		
		return new ResponseEntity<Optional<PeopleModel>>(user, HttpStatus.OK);
	}
	
	
	/**
	 *
	 * POST - Adiciona um novo registro
	 * 
	 */
	
	@PostMapping("/people")
	public ResponseEntity<PeopleModel> create
	(
			@Valid @RequestBody PeopleModel people,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<PeopleModel>(HttpStatus.BAD_REQUEST);
		
		PeopleModel user = mongodb.save(people);
		
		return new ResponseEntity<PeopleModel>(user, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * PUT = Atualiza um registro
	 * 
	 */
	
	@PutMapping("/people/{id}")
	public ResponseEntity<PeopleModel> update
	(
			@PathVariable String id, 
			@Valid @RequestBody PeopleModel people,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<PeopleModel>(HttpStatus.BAD_REQUEST);
		
		Optional<PeopleModel> result = mongodb.findById(id);
		result.get().setName(people.getName());
		result.get().setAge(people.getAge());
		
		PeopleModel user = mongodb.save(result.get());
		
		return new ResponseEntity<PeopleModel>(user, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * DELETE - Deleta um registro
	 * 
	 */
	
	@DeleteMapping("/people/{id}")
	public ResponseEntity<Boolean> delete
	(
			@PathVariable String id,
			@RequestHeader(value="Authorization", defaultValue="null") String token
	) 
	{
		if(!bearerToken.verify(token)) return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		
		try{
			
			mongodb.deleteById(id);			
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
}
