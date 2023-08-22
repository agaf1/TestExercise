package pl.aga.service.model;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class Transfer {
    String from;
    String to;
    double amount;
}
