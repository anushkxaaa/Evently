package com.evently.dto.Request;

import com.evently.grpc.Event;
import com.evently.grpc.EventCategory;

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
