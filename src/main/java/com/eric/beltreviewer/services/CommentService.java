package com.eric.beltreviewer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eric.beltreviewer.models.Comment;
import com.eric.beltreviewer.repositories.CommentRepository;

@Service
public class CommentService {
	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public List<Comment> findCommentsByEvent(Long id) {
		return commentRepository.findCommentsByEvent(id);
	}
}
