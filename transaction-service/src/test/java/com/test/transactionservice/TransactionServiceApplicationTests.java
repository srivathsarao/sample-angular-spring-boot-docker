package com.test.transactionservice;

import com.test.transactionservice.model.ClientInformation;
import com.test.transactionservice.model.ProductInformation;
import com.test.transactionservice.model.Transaction;
import com.test.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class TransactionServiceApplicationTests {

    public TransactionServiceApplicationTests() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    private TransactionRepository transactionRepository;

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void testFileParser() throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/Input.txt")));

        String line = null;

        long fileLength = 0;
        List<Transaction> transactionList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String clientType = line.substring(3, 6).trim();
            Integer clientNumber = Integer.parseInt(line.substring(7, 11).trim());
            Integer accountNumber = Integer.parseInt(line.substring(11, 15).trim());
            Integer subAccountNumber = Integer.parseInt(line.substring(15, 19).trim());
            ClientInformation clientInformation = new ClientInformation(clientNumber, accountNumber, clientType, subAccountNumber);

            String symbol = line.substring(31, 37).trim();
            String productGroupCode = line.substring(25, 27).trim();
            String exchangeCode = line.substring(27, 31).trim();
            Date expirationDate = new Timestamp(dateFormat.parse(line.substring(37, 45).trim()).getTime());

            ProductInformation productInformation = new ProductInformation(symbol, productGroupCode, exchangeCode, expirationDate);

            BigDecimal quantityLong = new BigDecimal(line.substring(63, 73).trim());
            BigDecimal quantityShort = new BigDecimal(line.substring(52, 62).trim());
            Date transactionDate = new Timestamp(dateFormat.parse(line.substring(121, 129).trim()).getTime());
            transactionList.add(new Transaction(fileLength++, clientInformation, productInformation, quantityShort.subtract(quantityLong).abs(), transactionDate));
        }

        List<Transaction> filteredItems = transactionRepository.findAll().stream().filter(item -> {
            return transactionList.stream().filter(directObject -> directObject.getClientInformation().equals(item.getClientInformation()) &&
                    directObject.getProductInformation().equals(item.getProductInformation()) &&
                    directObject.getTransactionDate().equals(item.getTransactionDate())).findFirst().isPresent();
        }).collect(Collectors.toList());

        assertEquals(transactionList.size(), transactionRepository.findAll().size());
        assertEquals(filteredItems.size(), transactionList.size());
    }
}
