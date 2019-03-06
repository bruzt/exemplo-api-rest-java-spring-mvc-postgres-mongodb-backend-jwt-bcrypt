package apirest.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Bcrypt {
	
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);
		
		public String hash(String password) {
			return bcrypt.encode(password);
		}
		
		public boolean compare(String rawPassword, String hashPassword) {
			return bcrypt.matches(rawPassword, hashPassword);
		}
}
