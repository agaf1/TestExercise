package pl.aga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Transfer;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class BankServiceTest {

    @Test
    void should_not_transfer_when_from_account_has_to_low_cash(){
        //given
        ValidationService validationService = Mockito.mock(ValidationService.class);

        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);

        Transfer transfer = new Transfer("account1","account2",5);

        Mockito.when(accountRepository.getBalance(Mockito.eq(transfer.getFrom()))).thenReturn(Double.valueOf(2));

        BankService bankService = new BankService(validationService,accountRepository);
        //when
        assertThatThrownBy(()->bankService.transfer(transfer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("to low cash on FromAccount ");
        //then
    }

}