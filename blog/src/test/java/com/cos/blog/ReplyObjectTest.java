package com.cos.blog;

import org.junit.jupiter.api.Test;

import com.cos.blog.model.Reply;

public class ReplyObjectTest {

	@Test
	public void toStringTest() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("ㅗㅑ")
				.build();
		
		System.out.println(reply); //오브젝트 출력시 toString 자동 호출 됨
	}
}
