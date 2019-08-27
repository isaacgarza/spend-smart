package com.spendsmart.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class CustomSchedule implements Serializable {

    private static final long serialVersionUID = 2150903486159423307L;

    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Date 1 is mandatory")
    private Date date1;

    @NotNull(message = "Date 2 is mandatory")
    private Date date2;
}
