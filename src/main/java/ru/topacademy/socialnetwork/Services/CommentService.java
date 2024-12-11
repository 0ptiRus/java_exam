package ru.topacademy.socialnetwork.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.topacademy.socialnetwork.Models.Comment;
import ru.topacademy.socialnetwork.Repositories.CommentRepository;

@Service
public class CommentService {
	 @Autowired
	    private CommentRepository commentRepository;

	    public Comment createComment(Comment comment) {
	        return commentRepository.save(comment);
	    }
}
