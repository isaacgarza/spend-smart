package com.spendsmart.dto;

import com.plaid.client.response.Account;
import com.plaid.client.response.AuthGetResponse.Numbers;
import com.plaid.client.response.ItemStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PlaidAuth {

    private ItemStatus itemStatus;
    private List<Account> accounts;
    private Numbers numbers;
}
