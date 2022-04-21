package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	 
	// 스프링이 로그인 요청을 가로챌 때 username, password를 가로채는데,
	// password가 틀렸을 때의 처리 등은 스프링 시큐리티가 알아서 한다.
	// 그래서 username이 db에 있는지만 확인해주면 됨. 이 함수에서 확인ㅇ함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("loadUserByUsername() 잔압");
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : "+username);
				});
		return new PrincipalDetail(principal); //유저 정보를 PrincipalDetail()에 담으면 시큐리티의 세션에 유저 정보가 저장된다.
		
	}


 
	
	
}
