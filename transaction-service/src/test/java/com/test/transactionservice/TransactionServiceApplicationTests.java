package com.test.transactionservice;

import com.test.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class TransactionServiceApplicationTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void contextLoads() {
        assertEquals(1, transactionRepository.findAll().size());
    }

}
