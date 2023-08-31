package pl.aga.service.model;

import lombok.ToString;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@ToString
public class TransactionHistory {
    Transaction transaction;
    ZonedDateTime dateTime;
    public TransactionHistory(Transaction transaction){
        this.transaction = transaction;
        this.dateTime = ZonedDateTime.now();
    }

    public TransactionHistory(Transaction transaction, ZonedDateTime dateTime){
        this.transaction = transaction;
        this.dateTime = dateTime;
    }
}
