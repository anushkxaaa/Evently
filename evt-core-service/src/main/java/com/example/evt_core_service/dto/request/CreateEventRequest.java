package com.example.evt_core_service.dto.request;

import com.example.evt_core_service.entity.EventCategory;

import java.util.UUID;

public record CreateEventRequest(
        String eventName,
        UUID organizerId,
        String organizerName,
        String organizerMobile,
        String city,
        EventCategory category
) {
}