package br.com.senac.servicestecinfo.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Message {
	
	private MessageSourceAccessor acessor;
	
	public Message(MessageSource messageSource) {
		
		 this.acessor = new MessageSourceAccessor(messageSource,LocaleContextHolder.getLocale());
	}
	
	public String get(String code) {
		return acessor.getMessage(code, LocaleContextHolder.getLocale());
	}
	
	public String get(MessageProperties messageProp) {
		return acessor.getMessage(messageProp.toString(), LocaleContextHolder.getLocale());
	}
	
	public String get(String code, String args) {
		return acessor.getMessage(code, args);
	}
	
	public String get(MessageSourceResolvable resolvable) {
		return acessor.getMessage(resolvable, LocaleContextHolder.getLocale());
	}
}
