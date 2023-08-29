package pl.aga.service.model;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class Transaction {
    String from;
    String to;
    double amount;
}
