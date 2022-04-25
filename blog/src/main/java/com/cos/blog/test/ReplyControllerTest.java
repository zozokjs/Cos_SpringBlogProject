package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		
		return boardRepository.findById(id).get();
		//무한 참조 발생
		/* board 모델을 요청하는데 거기엔 user와 reply 모델이 있고
		 * reply 모델에는 user와 board 모델이 있다. 그래서 무한 참조 발생.
		 * 
		 * */
	}
	

	
	@GetMapping("/test/reply")
	public List<Reply> getReply() {	
		return replyRepository.findAll();
	}
	

}
