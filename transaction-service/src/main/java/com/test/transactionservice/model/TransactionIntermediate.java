package com.test.transactionservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionIntermediate {
    private ClientInformation clientInformation;

    private ProductInformation productInformation;

    private BigDecimal quantityLong;

    private BigDecimal quantityShort;

    private String transactionDate;

    private String expirationDate;
}
