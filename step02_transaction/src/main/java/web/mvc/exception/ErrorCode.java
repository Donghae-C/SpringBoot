package web.mvc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
/**
 * Enum(열거형)은 서로 관련된 상수들을 정의하여 편리하게 사용하기 위한 자료형이다.
 * 
 * */
public enum ErrorCode { //enum은 'Enumeration' 의 약자로 열거, 목록 이라는 뜻

    FAILED_WITHDRAWAL_ACCOUNT(600, "출금계좌번호 오류입니다."),
    FAILED_DEPOSIT_ACCOUNT(601, "입금계좌번호 오류입니다."),
	FAILED_MAXIMUM( 603, "입금계좌 최대금액 초과 오류입니다.");

  private final int status;
  private final String msg;
}


