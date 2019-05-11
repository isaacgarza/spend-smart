package com.spendsmart.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subcategory implements Serializable {

    private static final long serialVersionUID = -3150890725159192993L;

    private UUID id;

    @NotNull(message = "Category is mandatory")
    private Category category;

    @NotBlank(message = "Name is mandatory")
    private String name;
}
