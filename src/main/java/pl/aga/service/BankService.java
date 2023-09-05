package pl.aga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.aga.repository.AccountRepository;
import pl.aga.service.model.Sort;
import pl.aga.service.model.Transaction;
import pl.aga.service.model.TransactionHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class BankService {
    private final ValidationService validationService;
    private final AccountRepository accountRepository;

    public void transfer(Transaction transaction) {
        log.info("start transfer " + transaction);
        validationService.checkInput(transaction);

        Double fromBalance = accountRepository.getBalance(transaction.getFrom());
        Double toBalance = accountRepository.getBalance(transaction.getTo());

        if (fromBalance < transaction.getAmount()) {
            log.error("transfer fail, to low cash on account={}, balance={}, transfer amount={}"
                    , transaction.getFrom(), fromBalance, transaction.getAmount());
            throw new IllegalArgumentException("to low cash on FromAccount ");
        }
        accountRepository.saveBalance(transaction.getFrom(), fromBalance - transaction.getAmount());
        accountRepository.saveBalance(transaction.getTo(), toBalance + transaction.getAmount());

        accountRepository.addTransactionHistory(new TransactionHistory(transaction));
    }

    List<TransactionHistory> getTransaction(String account, Sort sort) {
        List<TransactionHistory> transactions = accountRepository.getTransactions(account);

        List<TransactionHistory> sortedTransactions;

        Comparator<TransactionHistory> comparator = Comparator.comparing(TransactionHistory::getDateTime);

        if (sort.equals(Sort.DESC)) {
            comparator = comparator.reversed();
        }
        sortedTransactions = transactions.stream().sorted(comparator).toList();

        return sortedTransactions;
    }


}
