package br.com.senac.servicestecinfo.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Patterns {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@+[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"; 
	
	public static final Pattern pEmail = 
			Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

}
