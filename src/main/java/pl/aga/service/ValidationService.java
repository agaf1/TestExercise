package pl.aga.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pl.aga.service.model.Transaction;

@Slf4j
public class ValidationService {
    void checkInput(Transaction transaction){

        if(StringUtils.isBlank(transaction.getFrom())){
            log.info("Invalid from '{}'", transaction.getFrom());
            throw new IllegalArgumentException("Missing from");
        }

        if(StringUtils.isBlank(transaction.getTo())){
            throw new IllegalArgumentException("Missing to");
        }

        if(transaction.getAmount()<=0){
            throw new IllegalArgumentException("Invalid amount, amount="+ transaction.getAmount());
        }

    }

}
