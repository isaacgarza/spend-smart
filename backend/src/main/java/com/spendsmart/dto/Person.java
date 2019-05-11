package com.spendsmart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class Person implements Serializable {

    private static final long serialVersionUID = -5472418598913801305L;

    private UUID id;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Positive
    @Min(10)
    @Max(120)
    @NotNull(message = "Age is mandatory")
    private int age;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    private Timestamp createdTimestamp;

    private Timestamp updatedTimestamp;
}
