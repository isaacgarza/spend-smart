package com.spendsmart.dto;

import com.plaid.client.response.ErrorResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class PlaidTransaction implements Serializable {

    private static final long serialVersionUID = 7353405728324161722L;

    private Integer totalTransactions;

    private List<Transaction> transactions;

    private ErrorResponse error;
}
