package com.spendsmart.dto;

import com.plaid.client.response.Institution;
import com.plaid.client.response.ItemStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PlaidInstitution {

    private ItemStatus itemStatus;
    private Institution institution;
}
