package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	// input: postId, userId  output: X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdUserId(postId, userId);
		// 여부 => 삭제 or 추가
		if (count > 0) {
			likeMapper.deleteLike(postId, userId);
			return;
		} else {
			likeMapper.insertLike(postId, userId);
			return;
		}
	}
}
