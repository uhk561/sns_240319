package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

@Service
public class PostBO {

	@Autowired
	private PostRepository postRepository;

	// input: X
	// output: List<PostEntity>
	public List<PostEntity> getPostEntityList() {
		return postRepository.findByOrderByIdDesc();
	}
	
	public void addPost(int userId, String userLoginId, 
			String content, MultipartFile file) {

		String imagePath = null;
		
		if (file != null) {
			// 업로드 할 이미지가 있을 때에만 업로드
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		postMapper.insertPost(userId, content, imagePath);
	}
}