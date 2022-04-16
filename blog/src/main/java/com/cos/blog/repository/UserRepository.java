package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;


//user 테이블을 관리하는 repository 이 테아블의 fk는 integer 타입이다 라고 명시함.
//자동으로 bean 등록 됨
//@Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{

}
