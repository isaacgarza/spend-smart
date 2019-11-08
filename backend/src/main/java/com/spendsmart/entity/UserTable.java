package com.spendsmart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(length = 65, nullable = false)
    private String firstName;

    @Column(length = 65)
    private String lastName;

    @Column
    private int age;

    @Column(unique = true, length = 50)
    private String phoneNumber;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, length = 30)
    private String providerId;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createdTimestamp;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTimestamp;
}
