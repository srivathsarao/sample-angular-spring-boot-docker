package com.test.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(referencedColumnName = "clientNumber"),
            @JoinColumn(referencedColumnName = "accountNumber")
    })
    private ClientInformation clientInformation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "symbol")
    private ProductInformation productInformation;

    private BigDecimal totalTransactionAmount;

    private Date transactionDate;
}
