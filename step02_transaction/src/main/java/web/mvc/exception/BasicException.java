package web.mvc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
//public class BasicException extends RuntimeException {
public class BasicException extends Exception {//Checked 예외
	  private final ErrorCode errorCode;
}
