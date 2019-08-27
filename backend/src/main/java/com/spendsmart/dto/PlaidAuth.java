package com.spendsmart.dto;

import com.plaid.client.response.Account;
import com.plaid.client.response.AuthGetResponse.Numbers;
import com.plaid.client.response.ItemStatus;
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
public class PlaidAuth implements Serializable {

    private static final long serialVersionUID = 5781819439710925450L;

    private ItemStatus itemStatus;

    private List<Account> accounts;

    private Numbers numbers;
}
