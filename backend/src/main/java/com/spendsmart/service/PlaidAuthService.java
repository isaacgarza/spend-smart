package com.spendsmart.service;

import com.spendsmart.dto.PlaidAuth;
import com.spendsmart.dto.PlaidInstitution;
import com.spendsmart.dto.PlaidTransaction;

public interface PlaidAuthService {

    String getAccessToken();

    void publicTokenExchangeResponse(String publicToken);

    PlaidAuth getAuthResponse();

    PlaidInstitution getInstitutionResponse();

    PlaidTransaction getTransactionResponse(Integer numberOfDaysRequested);
}
