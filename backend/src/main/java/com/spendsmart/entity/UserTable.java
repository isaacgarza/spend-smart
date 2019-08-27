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
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class UserTable implements Serializable {

    private static final long serialVersionUID = -2204315205023049164L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 65, nullable = false)
    private String firstName;

    @Column(length = 65, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(unique = true, length = 50, nullable = false)
    private String phoneNumber;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createdTimestamp;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTimestamp;
}
