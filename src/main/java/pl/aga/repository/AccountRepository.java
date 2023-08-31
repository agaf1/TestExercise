package pl.aga.repository;

import pl.aga.service.model.Transaction;
import pl.aga.service.model.TransactionHistory;

import java.util.List;

public interface AccountRepository {
     Double getBalance(String account);
     void saveBalance(String account, Double amount);
     void addTransactionHistory(TransactionHistory transactionHistory);
     List<TransactionHistory> getTransactions(String account);

}
