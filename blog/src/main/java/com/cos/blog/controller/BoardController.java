package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	
	@Autowired
	private BoardService boardService;
	
	//2개 매핑됨.
	@GetMapping({"", "/"})
	//스프링 시큐리티의 로그인 된 세션에 접근하려면 
	//@AuthenticationPrincipal PrincipalDetail principal 형식으로 접근함
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC ) Pageable pageable) {
		
		model.addAttribute("boards",boardService.글목록(pageable));
		
		return "index";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		 model.addAttribute("board", boardService.글상세보기(id));
		 
		 return "board/detail";
	}
	
	//글 쓰기 창으로 이동함
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";		
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";
	}
	
}
