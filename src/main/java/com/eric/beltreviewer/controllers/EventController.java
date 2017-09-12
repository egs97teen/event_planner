package com.eric.beltreviewer.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eric.beltreviewer.models.Comment;
import com.eric.beltreviewer.models.Event;
import com.eric.beltreviewer.models.User;
import com.eric.beltreviewer.services.CommentService;
import com.eric.beltreviewer.services.EventService;
import com.eric.beltreviewer.services.UserService;
import com.eric.beltreviewer.validator.EventValidator;

@Controller
public class EventController {
	private EventService eventService;
	private EventValidator eventValidator;
	private UserService userService;
	private CommentService commentService;
	
	public EventController (EventService eventService, EventValidator eventValidator, UserService userService, CommentService commentService) {
		this.eventService = eventService;
		this.eventValidator = eventValidator;
		this.userService = userService;
		this.commentService = commentService;
	}
	
	@PostMapping("/createEvent")
	public String createEvent(Model model, @Valid @ModelAttribute("event") Event event, BindingResult result, Principal principal, @RequestParam("date") String date) {
		event.setDate(date);
		eventValidator.validate(event, result);
		
		if (result.hasErrors()) {
			String email = principal.getName();
			User currentUser = userService.findByEmail(email);
			
			List<Object[]> eventsInState = eventService.findEventsInState(currentUser.getState());
			List<Object[]> eventsOutOfState = eventService.findEventsOutOfState(currentUser.getState());
			
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("eventsInState", eventsInState);
			model.addAttribute("eventsOutOfState", eventsOutOfState);
			return "eventsView";
		} else {
			String email = principal.getName();
			User currentUser = userService.findByEmail(email);
			
			event.setHost(currentUser);
			
			eventService.saveEvent(event);
			return "redirect:/events";
		}
	}
	
	@RequestMapping("/events/{event_id}/edit")
	public String editEvent(@PathVariable("event_id") Long id, Model model, Principal principal) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		Event editEvent = eventService.findEventById(id);
		
		if (editEvent.getHost().getId() != currentUser.getId()) {
			return "redirect:/events";
		} else {
			model.addAttribute("editEvent", editEvent);
			return "editEventView";
		}
	}
	
	@PostMapping("/edit")
	public String editEvent(@Valid @ModelAttribute("editEvent") Event event, BindingResult result, Model model, @RequestParam("date") String editDate) {
		eventValidator.validate(event, result);
		if (result.hasErrors()) {
			model.addAttribute("editEvent", event);
			return "editEventView";
//			return "redirect:/events/"+Long.toString(event.getId())+"/edit";
		} else {
			event.setDate(editDate);
			eventService.updateEvent(event);
//			eventService.saveEvent(event);
			return "redirect:/events";
		}
	}
	
	@RequestMapping(value="/delete/{event_id}")
	public String deleteEvent(@PathVariable("event_id") Long id) {
		eventService.deleteEventById(id);
		return "redirect:/events";
	}
	
	@RequestMapping("/join/{event_id}")
	public String joinEvent(@PathVariable("event_id") Long id, Principal principal) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		
		eventService.userJoinEvent(currentUser, id);
		return "redirect:/events";
	}
	
	@RequestMapping("/cancel/{event_id}")
	public String cancelAttendance(@PathVariable("event_id") Long id, Principal principal) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		
		eventService.cancelUserAttendance(currentUser, id);
		return "redirect:/events";
	}
	
	@RequestMapping("/events/{event_id}")
	public String showEvent(@PathVariable("event_id") Long id, Model model, @Valid @ModelAttribute("comment") Comment comment) {
		Event event = eventService.findEventById(id);
		List<Comment> comments = commentService.findCommentsByEvent(id);
		
		model.addAttribute("comments", comments);
		model.addAttribute("event", event);
		return "eventDetailsView";
	}
}
