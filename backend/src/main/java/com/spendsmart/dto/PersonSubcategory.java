package com.spendsmart.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonSubcategory implements Serializable {

    private static final long serialVersionUID = 1523056740114465700L;

    private UUID id;

    @NotNull(message = "Person id is mandatory")
    private UUID personId;

    @NotNull(message = "Category is mandatory")
    private Category category;

    @NotBlank(message = "Name is mandatory")
    private String name;
}
