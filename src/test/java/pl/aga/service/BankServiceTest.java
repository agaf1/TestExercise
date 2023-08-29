package pl.aga.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Transaction;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {
    @Mock
    private ValidationService validationService;
    @Mock
    private AccountRepository accountRepository ;

    private final Transaction transaction = new Transaction("account1","account2",5);
    private final BankService bankService = new BankService(validationService,accountRepository);

    @Test
    void should_not_transfer_when_from_account_has_to_low_cash(){
        //given

        when(accountRepository.getBalance(eq(transaction.getFrom()))).thenReturn(Double.valueOf(2));

        //when
        assertThatThrownBy(()->bankService.transfer(transaction))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("to low cash on FromAccount ");
        //then
    }
    @Test
    void should_transfer_when_from_account_has_enough_cash(){

        when(accountRepository.getBalance(eq(transaction.getFrom()))).thenReturn(Double.valueOf(7));
        when(accountRepository.getBalance(eq(transaction.getTo()))).thenReturn(Double.valueOf(3));

        bankService.transfer(transaction);

        Mockito.verify(accountRepository).saveBalance(eq(transaction.getFrom()),eq(2d));

        Mockito.verify(accountRepository).saveBalance(eq(transaction.getTo()),eq(8.0));

    }

}