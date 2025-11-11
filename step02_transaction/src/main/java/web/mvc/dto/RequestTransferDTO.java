package web.mvc.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestTransferDTO {
    private String inAccount;//입금 계좌번호
    private String outAccount;//출금 계좌번호

    private int amount;


}
