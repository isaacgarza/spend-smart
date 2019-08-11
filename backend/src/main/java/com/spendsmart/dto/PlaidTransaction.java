package com.spendsmart.dto;

import com.plaid.client.response.ErrorResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PlaidTransaction {

    private Integer totalTransactions;
    private List<Transaction> transactions;
    private ErrorResponse error;
}
