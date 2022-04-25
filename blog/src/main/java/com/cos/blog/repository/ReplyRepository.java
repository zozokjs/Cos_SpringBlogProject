package com.cos.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	@Modifying
	//mSave()는 직접 만든 함수라서 쿼리를 명시해야 함...
	// (?1 등은 정해진 양식임)
	//쿼리에서 값 순서를 잘 맞춰야 함
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	public int mSave(int userId, int boardId, String content); //업데이트 된 행의 수를 리턴한다.
	
}
