package com.spendsmart.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class ExpenseRepeatSchedule implements Serializable {

    private static final long serialVersionUID = -3430739076786211990L;

    private UUID id;

    @NotBlank(message = "Type is mandatory")
    private String type;
}
