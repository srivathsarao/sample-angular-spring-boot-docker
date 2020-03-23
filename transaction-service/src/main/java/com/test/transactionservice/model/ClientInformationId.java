package com.test.transactionservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class ClientInformationId implements Serializable {
    private Integer clientNumber;
    private Integer accountNumber;
}
