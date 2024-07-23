package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.like.domain.Like;

@Mapper
public interface LikeMapper {
	
	public int likeToggle(int postId, int userId);
	
	public int selectLikeCountByPostIdUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public int insertLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public void deleteLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public int selectLikeByPostId(
			@Param("postId") int postId);

}
