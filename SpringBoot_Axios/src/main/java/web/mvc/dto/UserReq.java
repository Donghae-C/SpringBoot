package web.mvc.dto;

import lombok.*;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserReq {
    private String name;
    private String email;
    private int age;
}
