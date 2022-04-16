package com.cos.blog.model;



import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터가 들어가는 컬럼에 씀
	private String content;
	
	@ColumnDefault("0") //integer가 될거라서 홑따옴표가 없음. varchar로 만들거면 홑따옴표를 붙여야 함
	private int count ; //조회수
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //LAZY는 필요할 때만 들고 오라는 전략 
	//@JoinColumn(name="replyId") //필요 없음
	/*하나의 게시글은 여러 개의 댓글을 가진다.
	 * 게시글을 상세 조회할 때는 User, Board, Reply 테이블이 동시 조회되어야 한다.
	 * 원래는 join 쿼리를 만들어서 db에 날리지만 JPA 덕분에 그러지 않아도 된다.
	 * select * from board를 하면, Board 모델에는 User 객체가 있으므로 User 테이블을 자동으로 join해서 조회 된다.
	 * 이때는 reply 테이블도 함께 조회되어야 한다. 이를 위해 select * from reply를 날릴 필요 없이 여기서 명시하면 된다.
	 * 하나의 게시글은 여러 개의 댓글을 가지므로, 댓글은 여러 개가 될 것이므로 List로 받아야 한다.
	 * board 테이블에서 하나의 컬럼에는 reply가 하나씩 담겨야 한다.
	 * 그러므로 board 테이블에는 reply 컬럼이 생기지 않아야 한다. 이를 위해 mappedBy라고 명시한다.
	 * 	mappedBy 뒤에는, reply 클래스에서 FK로 명시된 것을 적는다.
	 * board 테이블을 조회하면서 reply 테이블에 있는 것도 조회하되, 
	 * reply 객체의 연관 관계의 주인은 reply 클래스의 필드 중, board에 있다는 뜻
	 * 그래서 이 코드를 실행하더라도 board 테이블에 reply 컬럼은 생기지 않는다.
	 * 단지 JOIN을 이용해 board 테이블을 select 할 때 reply 테이블 같이 select 되도록 하려고 명시하는 것임.	
	 *  * */
	private List<Reply> reply;
	
	@ManyToOne(fetch = FetchType.EAGER) //EAGER = 무조건 조회해야 한다는 전략
	@JoinColumn(name="userId")
	private User user; 
	// db는 오브젝트를 저장할 수 없지만 자바는 오브젝트를 저장할 수 있다.. 
	// 
	// User 테이블에 존재하는 특정값이 자동으로 FK로 설정된다.
	// joinColumn으로 지정했으므로 실제 db에는 userId가 컬럼명으로 저장된다.
	// ManyToOne을 지정 했다.
	 //Board = many, User = one , 한 명의 유저는 여러 개의 게시글을 쓸 수 있다.는 관계
	
	@CreationTimestamp //
	private Timestamp createDate;
	
	

}
