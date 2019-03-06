package apirest.models.mongodb;



import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

//import com.mongodb.lang.NonNull;

@Repository
@EnableMongoAuditing
@Document(collection="cl_people")
public class PeopleModel {

	@Id
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String age;

	@CreatedDate
    private Date createdAt;
    
	@LastModifiedDate
    private Date updatedAt;
	
	
	
	/////////////////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}		
}
