package com.spendsmart.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Category implements Serializable {

    private static final long serialVersionUID = -3157143185803008162L;

    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;
}
