package com.eric.beltreviewer.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eric.beltreviewer.models.Event;
import com.eric.beltreviewer.services.EventService;

@Component
public class EventValidator implements Validator {
	private EventService eventService;
	
	public EventValidator(EventService eventService) {
		this.eventService = eventService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.equals(clazz);
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		Event event = (Event) object;
		
		System.out.println("IN THE VALIDATOR" + event.getDate());
		
		if (event.getState() == null) {
			errors.rejectValue("state", "Check");
		}
		
		if (event.getDate() == null) {
			System.out.println("IN THE IF STATEMENT");
			errors.rejectValue("date",  "Blank");
		} else {
			Date currDate = new Date();
			Date eventDate = event.getDate();
			
			if (!currDate.before(eventDate)) {
				errors.rejectValue("date", "Check");
			}
		}
	}
}
