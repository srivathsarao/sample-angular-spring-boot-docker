package com.test.transactionservice.service;

import com.test.transactionservice.model.Transaction;
import com.test.transactionservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(Date date) {
        log.info("Getting transactions for the date ", date);
        Transaction transactionExample = new Transaction();
        transactionExample.setTransactionDate(date);
        return transactionRepository.findAll(Example.of(transactionExample));
    }
}
