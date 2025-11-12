package web.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Bank;
import web.mvc.dto.RequestTransferDTO;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.BankRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements  BankService{

    private final BankRepository bankRepository; //Spring Data JPA구현객체를 생성해서 주입

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int transfer(RequestTransferDTO requestTransferDTO) throws BasicException {
        //출금 계좌에서 금액 만큼 인출하기 -
       Bank outBank = bankRepository.findById(requestTransferDTO.getOutAccount())
                .orElseThrow(()->new BasicException(ErrorCode.FAILED_WITHDRAWAL_ACCOUNT));

        outBank.setBalance(outBank.getBalance()-requestTransferDTO.getAmount());
        System.out.println("출금완료");
        //입금계좌에 금액만큼 입금하기
       Bank intBank = bankRepository.findById(requestTransferDTO.getInAccount())
                .orElseThrow(()->new BasicException(ErrorCode.FAILED_DEPOSIT_ACCOUNT));

        intBank.setBalance(intBank.getBalance()+requestTransferDTO.getAmount());

        //잔액확인
        if(intBank.getBalance() > 1000){
            throw new BasicException(ErrorCode.FAILED_MAXIMUM);
        }

        return 1;
    }
    //readOnly 붙이는 이유? - 조회전용 트랜잭션으로 성능을 최적화하는 옵션
    @Transactional(readOnly = true)
    public List<Bank> findAll(){
        return bankRepository.findAll();
    }

}
