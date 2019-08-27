package com.spendsmart.dto;

import com.spendsmart.util.FundingScheduleEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Goal implements Serializable {

    private static final long serialVersionUID = -736603960548175997L;

    private UUID id;

    @NotNull(message = "User id is mandatory")
    private UUID userId;

    @NotNull(message = "Funding schedule type is mandatory")
    private FundingScheduleEnum fundingScheduleType;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @FutureOrPresent
    @NotNull(message = "Targeted date is mandatory")
    private Date targetedDate;

    @Positive
    @NotNull(message = "Targeted amount is mandatory")
    private BigDecimal targetedAmount;

    @PositiveOrZero
    @NotNull(message = "Saved amount is mandatory")
    private BigDecimal savedAmount;

    @NotNull(message = "Funding date is mandatory")
    private Date fundDate;

    private Timestamp createdTimestamp;

    private Timestamp updatedTimestamp;
}
