package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	
	@Transactional
	public void  회원수정(User user) {
		// 여기서 매개변수에 있는 User user는 외부에서 온 것임. 영속화 된 게 아님.
		// 수정 시에는 영속성 컨텍스트에서 User 오브젝트를 영속화 시킨 다음
		// 영속화된 User 오브젝트를 수정하는 게 가장 좋다.
		// 그래서 먼저 select를 해서 User 오브젝트를 db로부터 가져옴으로써 영속화시킴
		// 영속화 한 상태에서 오브젝트를 변경하면 자동으로 db에 update 문을 날려준다.
			
		User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		//ouath 값이 null이거나 비어 있을 때 수정함.
		//ouath 값이 있으면 수정 불가.
		if(persistence.getOauth() == null || persistence.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistence.setPassword(encPassword); //영속화 된 User 오브젝트에 암호화된 비번 세팅
			persistence.setEmail(user.getEmail());
		}
		
		// 이 함수(회원수정 함수)가 종료된다는 건 = 서비스 종료 = 트랜잭션 종료 = 자동으로 커밋 됨
		// = 영속화 된 persistence 객체에 변화가 감지되면 더티 체킹이 이뤄져서
		//변화된 것을 위해 update 문을 자동으로 날려준다는 것
	
	}
	
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		
		//username으로 db에서 찾아서 없으면? 새로운 User객체 리턴
		//findbyusername 결과가 null이면 새로운 User 객체를 리턴하고
		//아니면 기존 user 객체에 결과를 리턴한다.
		User user = userRepository.findByUsername(username).orElseGet(()->{
					return new User();
		});
		
		 /*
		User user = userRepository.findByUsername(username).orElseThrow(()->{
				return new IllegalArgumentException("회원 찾기 실패");
			});
		*/
		return user;

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
