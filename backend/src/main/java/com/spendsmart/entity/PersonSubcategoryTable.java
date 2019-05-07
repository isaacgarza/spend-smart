package com.spendsmart.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "person_subcategory")
public class PersonSubcategoryTable implements Serializable {

    private static final long serialVersionUID = 8079578623609092294L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID personId;

    @Column(nullable = false)
    private UUID categoryId;

    @Column(length = 65, unique = true, nullable = false)
    private String name;
}
