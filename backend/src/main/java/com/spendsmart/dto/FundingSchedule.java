package com.spendsmart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class FundingSchedule implements Serializable {

    private static final long serialVersionUID = -7845834463234523198L;

    private UUID id;

    @NotBlank(message = "Type is mandatory")
    private String type;
}
