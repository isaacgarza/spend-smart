package com.spendsmart.service.impl;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.AuthGetRequest;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.ItemGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.InstitutionsGetByIdResponse;
import com.plaid.client.response.ItemGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.ItemStatus;
import com.plaid.client.response.TransactionsGetResponse;
import com.spendsmart.dto.PlaidAuth;
import com.spendsmart.dto.PlaidInstitution;
import com.spendsmart.dto.PlaidTransaction;
import com.spendsmart.service.PlaidAuthService;
import com.spendsmart.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
public class PlaidAuthServiceImpl implements PlaidAuthService {

    private final PlaidClient plaidClient;

    private String accessToken;

    @Autowired
    public PlaidAuthServiceImpl(PlaidClient plaidClient) {
        this.plaidClient = plaidClient;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void publicTokenExchangeResponse(String publicToken) {
        try {
            sendPublicTokenExchangeRequest(publicToken);
        } catch (IOException e) {
            throw new ServiceException("Exception occurred getting Plaid access token", e);
        }
    }

    public PlaidAuth getAuthResponse() {
        try {
            return sendAuthRequest();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public PlaidInstitution getInstitutionResponse() {
        try {
            return sendInstitutionRequest();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public PlaidTransaction getTransactionResponse(Integer numberOfDaysRequested) {
        try {
            return sendTransactionRequest(numberOfDaysRequested);
        } catch (IOException e) {
            throw new ServiceException();
        }
    }

    private void sendPublicTokenExchangeRequest(String publicToken) throws IOException {
        Response<ItemPublicTokenExchangeResponse> response = this.plaidClient
                .service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken))
                .execute();
        if (response.isSuccessful()) {
            accessToken = response.body().getAccessToken();
        } else {
            throw new ServiceException(response.errorBody().string());
        }
    }

    private PlaidAuth sendAuthRequest() throws IOException {
        Response<AuthGetResponse> response = this.plaidClient
                .service()
                .authGet(new AuthGetRequest(accessToken))
                .execute();

        if (response.isSuccessful()) {
            return PlaidAuth.builder()
                    .itemStatus(response.body().getItem())
                    .accounts(response.body().getAccounts())
                    .numbers(response.body().getNumbers())
                    .build();
        } else {
            throw new ServiceException("Unable to pull accounts from the Plaid API.");
        }
    }

    private PlaidInstitution sendInstitutionRequest() throws IOException {
        Response<ItemGetResponse> itemResponse = this.plaidClient.service()
                .itemGet(new ItemGetRequest(accessToken))
                .execute();
        if (itemResponse.isSuccessful()) {
            return buildInstitutionResponse(itemResponse.body().getItem());
        } else {
            throw new ServiceException();
        }
    }

    private PlaidInstitution buildInstitutionResponse(ItemStatus item) throws IOException {
        Response<InstitutionsGetByIdResponse> institutionsResponse = this.plaidClient.service()
                .institutionsGetById(new InstitutionsGetByIdRequest(item.getInstitutionId()))
                .execute();
        if (institutionsResponse.isSuccessful()) {
            return PlaidInstitution.builder()
                    .itemStatus(item)
                    .institution(institutionsResponse.body().getInstitution())
                    .build();
        } else {
            throw new ServiceException();
        }
    }

    private PlaidTransaction sendTransactionRequest(Integer numberOfDaysRequested) throws IOException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -numberOfDaysRequested);
        Date startDate = cal.getTime();
        Date endDate = new Date();

        Response<TransactionsGetResponse> response = this.plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(accessToken, startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();

        return PlaidTransaction.builder()
                .totalTransactions(response.body().getTotalTransactions())
                .transactions(response.body().getTransactions())
                .error(!response.isSuccessful() ? this.plaidClient.parseError(response) : null)
                .build();
    }
}
