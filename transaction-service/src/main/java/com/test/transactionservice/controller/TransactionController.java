package com.test.transactionservice.controller;

import com.test.transactionservice.model.Transaction;
import com.test.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/transaction")
    public List<Transaction> getTransactions(@RequestParam("date") @DateTimeFormat(pattern="ddMMyyyy") Date date) {
        return transactionService.getTransactions(date);
    }

}
