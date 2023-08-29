package pl.aga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.aga.service.model.Transaction;

import static org.assertj.core.api.Assertions.*;
class ValidationServiceTest {
    private ValidationService valid = new ValidationService();

    @Test
    void should_throw_exception_when_from_is_blank(){
        Transaction transaction = new Transaction("   ","ala",56.98);

        assertThatThrownBy(()->valid.checkInput(transaction))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing from");

    }

    @Test
    void should_throw_exception_when_to_is_blank(){
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.getFrom()).thenReturn("from");
        Mockito.when(transaction.getTo()).thenReturn("");

        assertThatThrownBy(()->valid.checkInput(transaction))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing to");

    }
}