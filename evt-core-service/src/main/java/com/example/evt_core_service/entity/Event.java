package com.example.evt_core_service.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name="event")
public class Event extends BaseEntity{
    @Column(name="event_name", nullable = false)
    private String eventName;

    @Column(name="organizer_id",nullable = false)
    private UUID organizerId;

    @Column(name = "organizer_name", nullable = false)
    private String organizerName;

    @Column(name = "organizer_mobile",unique = true, nullable = false)
    private String organizerMobile;

    @Column(name="city", columnDefinition = "varchar(20) default 'DELHI'")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "category",nullable = false)
    private EventCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name ="status",nullable = false)
    private EventStatus status;

    @Column(name = "bannerImageKey")
    private String bannerImageKey;

    public Event(String eventName, UUID organizerId, String organizerName, String organizerMobile, String city, EventCategory category){
        this.eventName = eventName;
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.city = city;
        this.organizerMobile = organizerMobile;
        this.category = category;
        this.status = EventStatus.DRAFT;
    }

    protected Event(){
    }

    public void transitionStatusTo(EventStatus newStatus) {
        this.status = newStatus;
    }

    public void setBannerImageKey(String newBannerImageKey){
        this.bannerImageKey = newBannerImageKey;
    }
}