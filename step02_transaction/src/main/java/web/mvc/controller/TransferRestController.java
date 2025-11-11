package web.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.RequestTransferDTO;
import web.mvc.service.BankService;

@RestController //@Controller + ResponseBody
@RequiredArgsConstructor
@Slf4j
public class TransferRestController {
    private final BankService bankService;

    /**
     * 계좌이체
     * */
    @PostMapping("/transfer")
    //public ResponseEntity<?> transfer(@RequestBody RequestTransferDTO requestTransferDTO){
    public ResponseEntity<?> transfer(@RequestBody RequestTransferDTO requestTransferDTO)
              throws Exception{
         log.info("reqDTO ={}" ,  requestTransferDTO);

        int result =  bankService.transfer(requestTransferDTO);

        return  ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
