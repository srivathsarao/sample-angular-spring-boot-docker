package com.test.transactionservice;

import com.test.transactionservice.model.Transaction;
import com.test.transactionservice.repository.TransactionRepository;
import com.test.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void shouldReturnTransactionListGivenDate() throws ParseException {
        String dateString = "20100820";
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Date date = formatter.parse(dateString);

        List<Transaction> data = transactionService.getTransactions(date);
        assertEquals(1, data.size());
        assertEquals(transactionRepository.findAll().get(0), data.get(0));
    }
}
