package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

import ch.qos.logback.core.net.SyslogOutputStream;
import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;

@RestController
public class BoardApiController {
	    

	@Autowired
	private BoardService boardService;
	
	
	//회원가입 로직
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {

		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	

	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
		
	}

	//댓글 등록
	//데이터 받을 때 컨트롤러에서 dto를 만들어 주는 게 좋다.
	//큰 프로젝트는 데이터가 많이 왔다갔다 하기 때문
	//예를 들어 dto 클래스를 만들어서 그 객체에다 넣고 전달하는 식으로..
	@PostMapping("/api/board/{boardId}/reply")
	//주석은 dto 안 썼을 때
	//public ResponseDto<Integer> replySave( @PathVariable int boardId,  @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {

		//reply.setUser(principal.getUser());

		//boardService.댓글쓰기(principal.getUser(), boardId, reply);
		boardService.댓글쓰기(replySaveRequestDto);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	
}
