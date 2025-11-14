package web.mvc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class MemberAuthenticationException extends  RuntimeException{
    private final ErrorCode errorCode;
}
