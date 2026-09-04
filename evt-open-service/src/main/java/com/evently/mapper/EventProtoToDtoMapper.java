package com.evently.mapper;

import com.evently.dto.Response.EventResponse;
import com.evently.grpc.Event;
import java.time.Instant;
import java.util.UUID;

public class EventProtoToDtoMapper {

    private EventProtoToDtoMapper() {
    }

    private static Instant toInstant(com.google.protobuf.Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()); //factory method that constructs an instant from epoch seconds plus a nanosecond adjustment
    }

    public static EventResponse toDto(Event event) {
        return new EventResponse(
                UUID.fromString(event.getId()),
                event.getEventName(),
                UUID.fromString(event.getOrganizerId()),
                event.getOrganizerName(),
                event.getOrganizerMobile(),
                event.getCity(),
                event.getCategory().name(),
                event.getStatus().name(),
                event.getBannerImageKey().isBlank() ? null : event.getBannerImageKey(),
                toInstant(event.getCreatedOn()),
                toInstant(event.getModifiedOn())
        );
    }


}