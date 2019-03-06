package apirest.models.postgres;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Repository;


@Repository
@Entity
@Table(name="tb_users")
public class UserModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue //(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Column(unique=true)
	private String username;
	
	@NotNull
	private String password;
	
	@CreationTimestamp
    private Date createdAt;
    
    @UpdateTimestamp
    private Date updatedAt;
	
	
	
	///////////////////////

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
