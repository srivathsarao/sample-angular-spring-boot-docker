package com.test.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductInformation implements Serializable {
    @Id
    private String symbol;
    private String productGroupCode;
    private String exchangeCode;
    private Date expirationDate;
}
