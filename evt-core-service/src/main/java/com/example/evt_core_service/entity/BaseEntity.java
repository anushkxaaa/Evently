package com.example.evt_core_service.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(updatable = false,nullable = false)
    private UUID id;

    @CreatedDate
    @Column(name="created_on", nullable = false,updatable = false)
    private Instant createdOn;

    @LastModifiedDate
    @Column(name = "modified_on",nullable = false)
    private Instant modifiedOn;

    public UUID getId(){return id;}
    public Instant getCreatedOn(){return createdOn;}
    public Instant getModifiedOn(){return modifiedOn;}

}
