package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//이걸 붙여야 스프링이 bean에 등록해 줌(Ioc)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Transactional
	public int 회원가입(User user) {
		try {
			String rawPassword = user.getPassword(); //비밀번호 원문
			String encPassword = encoder.encode(rawPassword); //해시화된 비밀번호
	
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService 회원가입 ()"+e);
		}
		return  -1;
				
				
	}
	
	/* 시큐리티 없이 로그인하는 법
	//readOnly = true 이렇게하면
	//select할 때 트렌잭션 시작 되고 서비스 종료되면서 트렌잭션이 종료 될 텐데 이때까지의 데이터 정합성을 유지해준다.
	@Transactional(readOnly = true)
	public User 로그인(User user) {
		
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	
	}
	*/
	
}
