package com.airgear.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "goods")
public class SimpleGoods {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private GoodsVerificationStatus verificationStatus;

    private String name;

    private String description;
}