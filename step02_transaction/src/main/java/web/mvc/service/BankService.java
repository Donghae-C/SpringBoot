package web.mvc.service;

import web.mvc.dto.RequestTransferDTO;
import web.mvc.exception.BasicException;

public interface BankService {
    /**
     * 계좌이체
     * */
    public int transfer(RequestTransferDTO requestTransferDTO)throws BasicException;
}
