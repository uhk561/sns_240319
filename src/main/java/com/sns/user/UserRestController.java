package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;

	/**
	 * 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {

		// db 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);

		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) {
			result.put("is_duplicated_id", true);
		} else {
			result.put("is_duplicated_id", false);
		}
		return result;
	}

	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {

		// password md5 알고리즘 => hashing   
		// aaaa => 74b8733745420d4d33f80c4663dc5e5
		// aaaa => 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(password);

		// db insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);

		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
		@PostMapping("/sign-in")
		public Map<String, Object> signIn(
				@RequestParam("loginId") String loginId,
				@RequestParam("password") String password,
				HttpServletRequest request) {
			
			// password 해싱
			String hashedPassword = EncryptUtils.md5(password);
			
			// DB 조회 - loginId, 해싱된 비밀번호  => UserEntity
			UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
			
			// 로그인 처리 및 응답값
			Map<String, Object> result = new HashMap<>();
			if (user != null) { // <-- null이 아니면 로그인 성공
				// 세션에 사용자 정보를 담는다.(사용자 각각 마다)
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getId());
				session.setAttribute("userLoginId", user.getLoginId());
				session.setAttribute("userName", user.getName());
				
				result.put("code", 200);
				result.put("result", "성공");
			} else {  // 실패
				result.put("code", "403");
				result.put("error_message", "존재하지 않는 사용자입니다.");
			}
				
			
			return result;
		}
	}