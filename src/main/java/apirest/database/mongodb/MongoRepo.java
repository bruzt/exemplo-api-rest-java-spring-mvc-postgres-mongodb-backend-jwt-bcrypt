package apirest.database.mongodb;



import org.springframework.data.mongodb.repository.MongoRepository;

import apirest.models.mongodb.PeopleModel;



public interface MongoRepo extends MongoRepository<PeopleModel, String> {
	
	
	
}
