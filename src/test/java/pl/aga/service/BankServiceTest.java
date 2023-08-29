package pl.aga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Transfer;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BankServiceTest {

    private ValidationService validationService = Mockito.mock(ValidationService.class);

    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);

    private Transfer transfer = new Transfer("account1","account2",5);
    private BankService bankService = new BankService(validationService,accountRepository);

    @Test
    void should_not_transfer_when_from_account_has_to_low_cash(){
        //given

        when(accountRepository.getBalance(eq(transfer.getFrom()))).thenReturn(Double.valueOf(2));

        //when
        assertThatThrownBy(()->bankService.transfer(transfer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("to low cash on FromAccount ");
        //then
    }
    @Test
    void should_transfer_when_from_account_has_enough_cash(){

        when(accountRepository.getBalance(eq(transfer.getFrom()))).thenReturn(Double.valueOf(7));
        when(accountRepository.getBalance(eq(transfer.getTo()))).thenReturn(Double.valueOf(3));

        bankService.transfer(transfer);

        Mockito.verify(accountRepository).saveBalance(eq(transfer.getFrom()),eq(2d));

        Mockito.verify(accountRepository).saveBalance(eq(transfer.getTo()),eq(8.0));

    }

}