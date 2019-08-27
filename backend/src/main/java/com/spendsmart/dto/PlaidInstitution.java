package com.spendsmart.dto;

import com.plaid.client.response.Institution;
import com.plaid.client.response.ItemStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class PlaidInstitution implements Serializable {

    private static final long serialVersionUID = -7014601751586761306L;

    private ItemStatus itemStatus;

    private Institution institution;
}
