package pl.aga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Transaction;

@RequiredArgsConstructor
@Slf4j
public class BankService {
    private final ValidationService validationService;
    private final AccountRepository accountRepository;
    public void transfer(Transaction transaction){
        log.info("transfer "+ transaction);
        validationService.checkInput(transaction);

        Double fromBalance = accountRepository.getBalance(transaction.getFrom());
        Double toBalance = accountRepository.getBalance(transaction.getTo());

        if(fromBalance< transaction.getAmount()){
            throw new IllegalArgumentException("to low cash on FromAccount ");
        }
        accountRepository.saveBalance(transaction.getFrom(),fromBalance - transaction.getAmount());
        accountRepository.saveBalance(transaction.getTo(),toBalance + transaction.getAmount());
    }


}
