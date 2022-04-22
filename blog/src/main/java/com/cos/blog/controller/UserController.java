package com.cos.blog.controller;



import java.util.UUID;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.SyslogOutputStream;

//인증 안 된 사용자들이 출입할 수 있는 경로는 /auth/~로 허용
//또는 그냥 주소가  /이면  index.jsp로 갈텐데 그것도 허용
//static 이하에 있는 /js~ 파일이나 css/ ~ image/ ~ 등도 허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey; //절대 노출 불가
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {	
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {	
		return "user/updateForm";
	}
	
	//@ResponseBody 이렇게 붙이면 data를 리턴해주는 컨트롤러 함수가 된다.
	@GetMapping("/auth/kakao/callback") 
	public String kakaoCallback(String code) {
		
		//POST 방식으로 key-value 타입의 데이터를 카카오쪽으로 요청 해야 함
		//POST 방식으로 전달하기 편한 라이브러리 Retrofit2. OkHttp, RestTemplate
		RestTemplate rt = new RestTemplate();
		
		//HTTP HEADER 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HTTP BODY 오브젝트 생성
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("grant_type", "authorization_code");
		param.add("client_id", "e4fda2785138641bdc52d19ef40cf17c");
		param.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		param.add("code", code);

		//HTTP HEADER와 HTTP BODY를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(param, headers);
		
		//HTTP 요청하기 - POST - RESPONSE로 응답 받음.
		ResponseEntity <String >response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class				
				);
		
		//제이슨 벗겨서 자바 오브젝트로 만드는 라이브러리 - Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken	 = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {		
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		
		//System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		
		
		//카카오 사용자 정보 요청-------------------------------------------------
		
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);

		ResponseEntity <String >response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class				
				);
		
		//System.out.println("카카오 토큰 요청 완료  / 토큰 응답 : "+response2.getBody());
		
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile	 = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {		
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		
		//User 오브젝트 : username, password, email
		//System.out.println("카카오 아이디 번호 : "+kakaoProfile.getId());
		//System.out.println("카카오 이메일  : "+kakaoProfile.getKakao_account().getEmail() );
				
		//System.out.println("블로그 서버 username "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		//System.out.println("블로그 서버 이메일 "+kakaoProfile.getKakao_account().getEmail());
		
		//System.out.println("블로그 서버 패스워드 "+cosKey);
		
		//user.setUsername() 대신에 builder 사용
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		

		//가입된 사람인가? 비가입자인가?

		//kakaoUser로 얻은 username을 던져서... 그 결과를 받는다.
		//지금은 비가입자니까 새 객체를 리턴한다.
		User originUser =  userService.회원찾기(kakaoUser.getUsername());
	
		if(originUser.getUsername() == null) {
			//비가입자면 회원가입
			System.out.println("가입 안 된 회원입니다. 자동 회원 가입을 진행합니다");			
			userService.회원가입(kakaoUser);
		}
		
		//가입자면 곧바로 로그인처리
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		return "redirect:/"; 
	
		//return null;
	}
	
}
