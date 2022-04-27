package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

//이걸 붙여야 스프링이 bean에 등록해 줌(Ioc)
@Service
public class BoardService {

	
	/** 
	@Autowired한 것과 같은 효과
	private BoardRepository boardRepository;
	private ReplyRepository replyRepository;
	
	public BoardService(BoardRepository bRepo, ReplyRepository rRepo ) {
		this.boardRepository = bRepo;
		this.replyRepository = rRepo;
	}
	*/
	
	
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void  글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
				
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
	
		return boardRepository.findAll(pageable);

	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세 보기 실패 : 아이디 찾을 수 없음");
				});
	}
		
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 수정 실패 : 아이디를 찾을 수 없음");
				});//영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setTitle(requestBoard.getContent());
		//이 함수 종료 시(service 종료) 트랜잭션 종료 됨. 이때 더티채킹이 발생. 자동 업데이트 됨(db쪽으로 flush)
	}
	
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

	
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	
}
