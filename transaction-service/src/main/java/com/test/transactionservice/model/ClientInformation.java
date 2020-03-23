package com.test.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Data
@Entity
@IdClass(ClientInformationId.class)
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformation implements Serializable {
    @Id
    private Integer clientNumber;
    @Id
    private Integer accountNumber;
    private String clientType;
    private Integer subAccountNumber;
}
