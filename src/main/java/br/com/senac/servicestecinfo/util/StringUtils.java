package br.com.senac.servicestecinfo.util;

import java.util.UUID;
import java.util.regex.Matcher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StringUtils {

	public static String generatePass() {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return myRandom.substring(0, 8);
	}

	public String encrypthPass(String newPassword) {
		try {
			BCryptPasswordEncoder password = new BCryptPasswordEncoder();
			return password.encode(newPassword);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean isEmail(String email) {
		Matcher match = Patterns.pEmail.matcher(email);
		return match.matches();
	}
}
