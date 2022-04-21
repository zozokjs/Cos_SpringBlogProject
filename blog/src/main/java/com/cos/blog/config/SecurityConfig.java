package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;


//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //빈등록 하기
@EnableWebSecurity 
//@EnableWebSecurity  -> 스프링 시큐리티라는 필터를 추가하는 것 
//  = 스프링 시큐리티가 이미 활성화 되어 있는데, 어떤 설정을 이 파일에서 하겠다는 뜻
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	
	@Bean // 이거 붙여서 ioc 관리되게 함
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
	//시큐리티가 대신 로그인할 때 password를 가로채는데
	//이 password가 어떤 것으로 해쉬 되었는지 알아야 
	//같은 해쉬로 암호화해서 db에 있는 해쉬와 비교할 수 있다. 그래야 로그인 가능함	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("ww");
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트 할 때는 걸어두는 게 좋다)
			.authorizeRequests() //request가 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")  //이 경로로 온다면
				.permitAll() // 누구나 들어올 수 있다.
				.anyRequest() //그 외의 경로는
				.authenticated() //인증을 해야 한다.
			.and()
				.formLogin() //인증이 필요한 곳으로 요청이 오면
				.loginPage("/auth/loginForm") // 이 페이지로 이동하라
				.loginProcessingUrl("/auth/loginProc") //해당 주소로 요청되는 로그인을 스프링 시큐리티가 가로채고 대신 로그인 한다.
				.defaultSuccessUrl("/"); //로그인이 성공하면 이 페이지로 이동한다.
				
	}	
	
	
	
}
