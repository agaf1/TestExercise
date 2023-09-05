package pl.aga.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Sort;
import pl.aga.service.model.Transaction;
import pl.aga.service.model.TransactionHistory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {
    @Mock
    private ValidationService validationService;
    @Mock
    private AccountRepository accountRepository ;
    @InjectMocks
    private BankService bankService;
    private final Transaction transaction = new Transaction("account1","account2",5);

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

        verify(accountRepository).saveBalance(eq(transaction.getFrom()),eq(2d));

        verify(accountRepository).saveBalance(eq(transaction.getTo()),eq(8.0));

        verify(accountRepository).addTransactionHistory(Mockito.argThat(
                history-> history.getTransaction().equals(transaction) && history.getDateTime() != null));


    }

    @ParameterizedTest
    @MethodSource("dataForTransactionSort")
    void should_sort_list_of_transaction_by_date(Sort sortOrder,List<TransactionHistory> actual,List<TransactionHistory>expected){
        //given

        when(accountRepository.getTransactions(anyString())).thenReturn(actual);

        //when

        List<TransactionHistory> sortedTransactions = bankService.getTransaction(anyString(),sortOrder);

        //then
        assertThat(sortedTransactions).containsExactlyElementsOf(expected);
    }

    private static Stream<Arguments> dataForTransactionSort(){
        TransactionHistory history1 = transactionalHistoryWithMockedTransaction(
                ZonedDateTime.of(2023,8,31,12,00,00,00, ZoneId.systemDefault()));
        TransactionHistory history2 = transactionalHistoryWithMockedTransaction(
                ZonedDateTime.of(2023,9,2,12,00,00,00,ZoneId.systemDefault()));
        TransactionHistory history3 = transactionalHistoryWithMockedTransaction(
                ZonedDateTime.of(2023,8,12,8,00,00,00,ZoneId.systemDefault()));

        List<TransactionHistory> actual = List.of(history1,history2,history3);
        List<TransactionHistory> expectedSortedAsc = List.of(history3,history1,history2);
        List<TransactionHistory> expectedSortedDesc = List.of(history2,history1,history3);

        return Stream.of(
                Arguments.of(Sort.ASC,actual,expectedSortedAsc),
                Arguments.of(Sort.DESC,actual,expectedSortedDesc)
        );
    }
    private static TransactionHistory transactionalHistoryWithMockedTransaction(ZonedDateTime addTime){
        Transaction transaction = Mockito.mock(Transaction.class);
        return  new TransactionHistory(transaction,
                addTime);

    }

}