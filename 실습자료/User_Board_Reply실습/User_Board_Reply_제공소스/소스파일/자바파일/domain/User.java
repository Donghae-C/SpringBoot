package web.mvc.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users") //테이블이름 변경
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id //pk
   private String userId;
	
   private String pwd;
   private String name;
}
