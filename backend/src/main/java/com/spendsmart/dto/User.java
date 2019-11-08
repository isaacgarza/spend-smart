package com.spendsmart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

    private static final long serialVersionUID = -5472418598913801305L;

    private UUID id;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotNull
    private Boolean emailVerified = false;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Positive
    @Max(120)
    private int age;

    private String phoneNumber;

    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    private String provider;

    @NotBlank(message = "Provider ID is mandatory")
    private String providerId;

    private Timestamp createdTimestamp;

    private Timestamp updatedTimestamp;
}
