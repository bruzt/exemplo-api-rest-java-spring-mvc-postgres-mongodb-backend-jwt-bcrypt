package apirest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyBearerToken {
	
	@Autowired
	JsonWebToken jwt;
	
	public boolean verify(String bearer) {
		
		String[] splitToken = bearer.split("\\s+");
		
		if(splitToken.length != 2) return false;
		
		if(!splitToken[0].equals("Bearer")) return false;
		
		if(!jwt.verifyJwt(splitToken[1])) return false;
		
		return true;
	}
}
