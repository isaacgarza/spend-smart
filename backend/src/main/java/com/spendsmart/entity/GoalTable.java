package com.spendsmart.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "goal")
public class GoalTable implements Serializable {

    private static final long serialVersionUID = -7710367690524083867L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID fundingScheduleTypeId;

    @Column(nullable = false)
    private UUID personId;

    @Column(length = 65, nullable = false)
    private String name;

    @Column(nullable = false)
    private Date targetedDate;

    @Column(nullable = false)
    private BigDecimal targetedAmount;

    @Column(nullable = false)
    private BigDecimal savedAmount;

    @Column(nullable = false)
    private Date fundDate;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createdTimestamp;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTimestamp;
}
