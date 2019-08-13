package com.spendsmart.controller;

import com.spendsmart.dto.PlaidTransaction;
import com.spendsmart.service.PlaidAuthService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/plaid")
public class PlaidController {

    private final PlaidAuthService authService;

    @Autowired
    public PlaidController(PlaidAuthService authService) {
        this.authService = authService;
    }

    /**
     * Exchange link public token for access token.
     */
    @PostMapping(value="/access/token", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity getAccessToken(@RequestParam String publicToken) {
        try {
            authService.publicTokenExchangeResponse(publicToken);
            return ResponseEntity.noContent().build();
        } catch (ServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData(e.getMessage()));
        }
    }

    /**
     * Retrieve high-level account information and account and routing numbers
     * for each account associated with the Item.
     */
    @GetMapping(value="/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getAccount() {
        try {
            if (authService.getAccessToken() != null) {
                return ResponseEntity.ok().body(authService.getAuthResponse());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData("Unable to pull accounts from the Plaid API."));
        }
    }

    /**
     * Pull the Item - this includes information about available products,
     * billed products, webhook information, and more.
     */
    @PostMapping(value="/item", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getItem() {
        try {
            if (authService.getAccessToken() != null) {
                return ResponseEntity.ok().body(authService.getInstitutionResponse());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(getErrorResponseData(ExceptionConstants.NOT_AUTHORIZED));
            }
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData("Unable to pull item information from the Plaid API."));
        }
    }

    /**
     * Pull transactions for the Item for the last n days.
     */
    @PostMapping(value="/transactions/{numberOfDaysRequested}", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions(@PathVariable Integer numberOfDaysRequested) {
        try {
            if (authService.getAccessToken() != null) {
                return getTransactionsResponse(numberOfDaysRequested);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(getErrorResponseData(ExceptionConstants.NOT_AUTHORIZED));
            }
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData("Unable to pull transaction information from the Plaid API."));
        }
    }

    private ResponseEntity getTransactionsResponse(Integer numberOfDaysRequested) {
        PlaidTransaction plaidTransaction = authService.getTransactionResponse(numberOfDaysRequested);
        if (plaidTransaction.getError() == null) {
            return ResponseEntity.ok().body(plaidTransaction);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(plaidTransaction.getError());
        }
    }

    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put(ExceptionConstants.ERROR, false);
        data.put("message", message);
        return data;
    }
}
