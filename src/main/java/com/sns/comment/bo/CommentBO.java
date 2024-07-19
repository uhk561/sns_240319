package com.sns.comment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.mapper.CommentMapper;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	//  input: postId, userId, content
	// output: X
	
	public void addComment(int userId, int postId, 
			String content) {
		
		commentMapper.insertComment(userId, postId, content);
	}
}
