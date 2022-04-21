package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;


//DAO
//user 테이블을 관리하는 repository 이 테아블의 fk는 integer 타입이다 라고 명시함.
//자동으로 bean 등록 됨
//@Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{

	//함수 앞에 'findBy대문자' 이렇게 붙으면 아래의 쿼리가 자동 실행됨
	//SELECT * from user where username = ? 
	Optional<User> findByUsername(String username);
	
	
	
	
	
	
	
	
	
	
	
	
	/* 시큐리티 없이 로그인하는 법
	//JPA Naming 쿼리 방식으로 함수를 만들면
	//select * from user where username = ? AND password =? 
	//이런 쿼리가 자동 실행
	// findBy...이렇게 시작해야 함
	//매개변수 순서에 따라 ?에 들어간다.
	//UserService에서 동작함
	User findByUsernameAndPassword(String username, String password);
	
	// 위와는 다른 방법이지만 똑같이 동작함 
	@Query(value="select * from user where username = ? AND password =?", nativeQuery = true);
	User login(String username, String password);
	
	*/
}
