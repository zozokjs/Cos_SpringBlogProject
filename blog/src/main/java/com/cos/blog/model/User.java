package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//User 클래스가 MySQL에 테이블 생성됨

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity
// @DynamicInsert //insert할 때 null인 필드를  제외해준다.
public class User {
	
	@Id //primary key가 된다.
	//프로젝트에서 연결된 db의 넘버링 전략을 따라간다.	
	//예) 오라클 쓰면 sequence를, mysql 쓰면 auto_increment가 자동으로 사용된다는 뜻
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; //auto increment
	
	//null이 될 수 없게 한다. 길이를 지정한다.
	//unique 넣어서 중복 될 수 없게 한다.
	@Column(nullable = false, length = 100, unique = true)
	private String username; //아이디
	
	
	//nullable = false하면 null이 될 수 없게 한다.
	//password가 null 될수 있음
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	
	//관리자, 일반유저를 구분하는 값을 지정할 것임
	//@ColumnDefault(" 'user' ")
	//DB는 RoleType이라는 데이터 타입이 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋음 // ADMIN, USER
	
	//현재 시간이 자동으로 들어가도록 한다.
	@CreationTimestamp
	private Timestamp createDate;

	private String oauth; //kakao로그인 했으면 kakao 들어감...

	
}
