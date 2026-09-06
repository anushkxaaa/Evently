package com.example.evt_core_service.dto.response;


import com.example.evt_core_service.entity.Event;
import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.entity.EventStatus;

import java.time.Instant;
import java.util.UUID;

public record EventResponse(
        UUID id,
        String eventName,
        UUID organizerId,
        String organizerName,
        String organizerMobile,
        String city,
        EventCategory category,
        EventStatus status,
        String bannerImageKey,
        Instant createdOn,
        Instant modifiedOn


) {
    public static EventResponse from(Event event){
        return new EventResponse(
                event.getId(),
                event.getEventName(),
                event.getOrganizerId(),
                event.getOrganizerName(),
                event.getOrganizerMobile(),
                event.getCity(),
                event.getCategory(),
                event.getStatus(),
                event.getBannerImageKey(),
                event.getCreatedOn(),
                event.getModifiedOn()
        );
    }

}
