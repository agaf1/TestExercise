package pl.aga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.aga.service.model.Transfer;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
class ValidationServiceTest {
    private ValidationService valid = new ValidationService();

    @Test
    void should_throw_exception_when_from_is_blank(){
        Transfer transfer = new Transfer("   ","ala",56.98);

        assertThatThrownBy(()->valid.checkInput(transfer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing from");

    }

    @Test
    void should_throw_exception_when_to_is_blank(){
        Transfer transfer = Mockito.mock(Transfer.class);
        Mockito.when(transfer.getFrom()).thenReturn("from");
        Mockito.when(transfer.getTo()).thenReturn("");

        assertThatThrownBy(()->valid.checkInput(transfer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing to");

    }
}