package pl.aga.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pl.aga.service.model.Transfer;

@Slf4j
public class ValidationService {
    void checkInput(Transfer transfer ){

        if(StringUtils.isBlank(transfer.getFrom())){
            log.info("Invalid from '{}'",transfer.getFrom());
            throw new IllegalArgumentException("Missing from");
        }

        if(StringUtils.isBlank(transfer.getTo())){
            throw new IllegalArgumentException("Missing to");
        }

        if(transfer.getAmount()<=0){
            throw new IllegalArgumentException("Invalid amount, amount="+transfer.getAmount());
        }

    }

}
