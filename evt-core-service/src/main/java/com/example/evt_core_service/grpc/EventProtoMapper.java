package com.example.evt_core_service.grpc;

import com.evently.grpc.Event;
import com.example.evt_core_service.dto.response.EventResponse;
import com.google.protobuf.Timestamp;

import java.time.Instant;

public final class EventProtoMapper {

    private EventProtoMapper() {
    }

    public static Event toProto(EventResponse event) {
        Event.Builder builder = Event.newBuilder()
                .setId(event.id().toString())
                .setEventName(nullToEmpty(event.eventName()))
                .setOrganizerId(event.organizerId().toString())
                .setOrganizerName(nullToEmpty(event.organizerName()))
                .setOrganizerMobile(nullToEmpty(event.organizerMobile()))
                .setCity(nullToEmpty(event.city()))
                .setCategory(EventEnumMapper.toProto(event.category()))
                .setStatus(EventEnumMapper.toProto(event.status()))
                .setBannerImageKey(nullToEmpty(event.bannerImageKey()));

        if (event.createdOn() != null) {
            builder.setCreatedOn(toProtoTimestamp(event.createdOn()));
        }
        if (event.modifiedOn() != null) {
            builder.setModifiedOn(toProtoTimestamp(event.modifiedOn()));
        }

        return builder.build();
    }
    private static Timestamp toProtoTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}