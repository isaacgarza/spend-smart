package com.spendsmart.dto;

import com.spendsmart.util.ExpenseRepeatScheduleEnum;
import com.spendsmart.util.FundingScheduleEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Expense implements Serializable {

    private static final long serialVersionUID = 2526986387494638823L;

    private UUID id;

    @NotNull(message = "Person id is mandatory")
    private UUID personId;

    @NotNull(message = "Expense repeat schedule type is mandatory")
    private ExpenseRepeatScheduleEnum expenseRepeatScheduleType;

    @NotNull(message = "Funding schedule type is mandatory")
    private FundingScheduleEnum fundingScheduleType;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @FutureOrPresent
    @NotNull(message = "Repeat date is mandatory")
    private Date repeatDate;

    @Positive
    @NotNull(message = "Targeted amount is mandatory")
    private BigDecimal targetedAmount;

    @NotNull(message = "Saved amount is mandatory")
    private BigDecimal savedAmount;

    @NotNull(message = "Funding date is mandatory")
    private Date fundDate;

    private Timestamp createdTimestamp;

    private Timestamp updatedTimestamp;
}
