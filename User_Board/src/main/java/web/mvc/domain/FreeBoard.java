package web.mvc.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Table(name = "이름설정..")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString//(exclude = "repliesList")
public class FreeBoard { //db에 free_board
	@Id //pk
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String subject;
	private String writer;
	
	@Column(length = 500)
	private String content;
	private String password;
	private int readnum; //조회수
	
	@CreationTimestamp
	private LocalDateTime insertDate; //등록일
	
	@UpdateTimestamp
	private LocalDateTime updateDate; //수정
	
	
	//부모글 한개에 딸린 댓글 정보 
	/**
	 * cascade sms  Entity 의 상태변화가 생기면 연관관계 있는
	 *  Entity도 상태변화를 전이시키는 옵션
	 * */
	@OneToMany(mappedBy = "freeBoard" , cascade = CascadeType.ALL, fetch = FetchType.LAZY) //  지연로딩
	//@OneToMany(mappedBy = "freeBoard") //  지연로딩
	private List<Reply> repliesList;
	
	
	public FreeBoard(Long bno) {
		this.bno=bno;
	}
	
	
  
}











