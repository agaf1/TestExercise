package pl.aga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Transfer;

@RequiredArgsConstructor
@Slf4j
public class BankService {
    private final ValidationService validationService;
    private final AccountRepository accountRepository;
    public void transfer(Transfer transfer){
        log.info("transfer "+transfer);
        validationService.checkInput(transfer);

        Double fromBalance = accountRepository.getBalance(transfer.getFrom());

        if(fromBalance<transfer.getAmount()){
            throw new IllegalArgumentException("to low cash on FromAccount ");
        }

    }


}
