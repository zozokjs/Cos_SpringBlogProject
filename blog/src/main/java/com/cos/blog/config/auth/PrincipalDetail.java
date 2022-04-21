package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인 진행하고 완료 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 저장소에 저장한다.
@Getter
public class PrincipalDetail implements UserDetails {

	private User user; //컴포지션

	public PrincipalDetail(User user) {
			this.user= user;	
	}
	
	
	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정의 만료 여부를 리턴(true = 만료 안 됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정의 잠김 여부를 리턴(true = 안 잠김)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호의 만료 여부를 리턴(true = 만료 안 됨)		
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 활성화 여부를 리턴(true = 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	

	//계정이 가진 권한 목록을 리턴(권한이 여러 개일 때는 루프를 돌아서 검사해야 함)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*
		collectors.add(new GrantedAuthority() {			
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole(); //ROLE_는 정해진 규칙이다. 이걸 안 붙이면 인식 못함.
			}
		});
		*/
		//위 아래는 동일
		collectors.add(()->{ return "ROLE_"+user.getRole();});
		

		return collectors;
	}
	
}
