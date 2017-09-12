package com.eric.beltreviewer.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.eric.beltreviewer.models.Comment;
import com.eric.beltreviewer.models.Event;
import com.eric.beltreviewer.models.User;
import com.eric.beltreviewer.services.CommentService;
import com.eric.beltreviewer.services.EventService;
import com.eric.beltreviewer.services.UserService;

@Controller
public class CommentController {
	private CommentService commentService;
	private EventService eventService;
	private UserService userService;
	
	public CommentController (CommentService commentService, EventService eventService, UserService userService) {
		this.commentService = commentService;
		this.eventService = eventService;
		this.userService = userService;
	}
	
	@PostMapping("/addComment")
	public String addComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult result, Principal principal, Model model) {
		Event commentEvent = comment.getEvent();
		Event event = eventService.findEventById(commentEvent.getId());
		
		model.addAttribute("event", event);
		if (result.hasErrors()) {
			List<Comment> comments = commentService.findCommentsByEvent(event.getId());
			model.addAttribute("comments", comments);
			return "eventDetailsView";
		} else {
			String email = principal.getName();
			User currentUser = userService.findByEmail(email);
			
			comment.setCommenter(currentUser);
			commentService.saveComment(comment);
			
			List<Comment> comments = commentService.findCommentsByEvent(event.getId());
			model.addAttribute("comments", comments);
			return "eventDetailsView";
		}
	}
}
