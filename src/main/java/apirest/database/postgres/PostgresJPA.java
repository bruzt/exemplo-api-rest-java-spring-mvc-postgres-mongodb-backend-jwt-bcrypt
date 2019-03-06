package apirest.database.postgres;


import org.springframework.data.jpa.repository.JpaRepository;

import apirest.models.postgres.UserModel;

public interface PostgresJPA extends JpaRepository<UserModel, Long> {
	
	UserModel findByUsername(String usernaname);
	
}
