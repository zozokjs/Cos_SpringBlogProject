package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.persistence.Entity;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.data.domain.Sort;

@RestController
public class DummyControllerTest {

	//UserRepository 타입으로 스프링이 관리하는 객체가 있다면 넣어준다.
	@Autowired //의존성 주입된다.
	private UserRepository userRepository;

	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {			
			return "삭제 실패  해당 id는 db에 없음: "+id;
		}
		
		return "삭제 됨 id : "+id ;
		
	}
	
	
	
	
	//Postman으로 json 데이터 만들어서 put 요청함.
	//json으로 요청 했는데, 스프링에서 messageConverter가 jackson 라이브러리 사용해서
	//자바 오브젝트로 변환해서 출력해줬음. 이때 @RequestBody를 쓴다.
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id :" +id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
				
		//null 검사하여 null 아니면 객체에 세팅해서 save()로 업데이트
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정 실패");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		
		requestUser.setId(id);
		
		
		//save()는 id를 전달 안 하면 insert 해주고
		// 전달하면 해당 id 데이터가 있으면 update 해주고
		// 전달하면 해당 id 데이터가 없으면 insert 해준다.
		//userRepository.save(user);
		
		//ㄷㅓ티 채킹
		return user;
		
	}
	
	
	
	//모든 유저 얻기
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	

	
	// 1페이지 당 2건의 데이터 리턴하여 페이징 할 것임
	// 2건씩, id 기준으로 정렬하되, 최신순으로
	// http://localhost:8000/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC ) Pageable pageable){
		Page <User> pagingUser= userRepository.findAll(pageable);
		
		List<User> users  = pagingUser.getContent();
		return users;
	}
	
	
	
	
	// {id}주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//id로 db 검색을 하는데, db에 존재하지 않는 id로 검색하면 null이 리턴된다.
		//null을 리턴 받으면 프로그램에 문제가 생길 수 있으니 null인지 아닌지 판단해서 return 하도록 한다.
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {			
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : "+id);
			}
		});
		
		//람다식으로 처리한다면
		/*
		User user2 = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 없습니다");
		});
		*/
		
		//user 객체는 자바 오브젝트임.
		//요청은 웹 브라우저에서 했음. 웹 브라우저는 HTML만 인식 가능
		//그래서 user 객체를 웹 브라우저가 이해하는 데이터로 변환해야 함(json)
		//스프링부트는 messageConverter가 응답 시 자동으로 작동한다.
		//그래서 자바 오브젝트를 리턴하면 메시지컨버터가 Jackson 라이브러리르 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 전달한다.
		return user;
		
	}
	
	
	
	// http://localhost:8000/blog/dummy/join으로 요청
	//http의 body에 username, pasword, email 데이터를 갖고 요청.
	@PostMapping("/dummy/join")
	public String join(User userl) {
		System.out.println("id"+userl.getId());
		System.out.println("username " +userl.getUsername());
		System.out.println("password " +userl.getPassword());
		System.out.println("email " + userl.getEmail());
		System.out.println("role "+userl.getRole());
		System.out.println("date "+userl.getCreateDate());
		
		userl.setRole(RoleType.USER);
		userRepository.save(userl);
		return"회원가입이 완료되었습니다";
		
	}
	
}
