package com.example.evt_core_service.grpc;

import com.example.evt_core_service.entity.Event;
import com.google.protobuf.Timestamp;

import java.time.Instant;

public class EventProtoMapper {

    private EventProtoMapper() {
    }

    public static com.evently.grpc.Event toProto(Event event) {
        com.evently.grpc.Event.Builder builder = com.evently.grpc.Event.newBuilder()
                .setId(event.getId().toString())
                .setEventName(event.getEventName())
                .setOrganizerId(event.getOrganizerId().toString())
                .setOrganizerName(event.getOrganizerName())
                .setOrganizerMobile(event.getOrganizerMobile())
                .setCity(event.getCity())
                .setCategory(EventEnumMapper.toProto(event.getCategory()))
                .setStatus(EventEnumMapper.toProto(event.getStatus()))
                .setCreatedOn(toProtoTimestamp(event.getCreatedOn()))
                .setModifiedOn(toProtoTimestamp(event.getModifiedOn()));

        if (event.getBannerImageKey() != null) {
            builder.setBannerImageKey(event.getBannerImageKey());
        }

        return builder.build();
    }

    private static Timestamp toProtoTimestamp(Instant instant) {
        if (instant == null) {
            return Timestamp.getDefaultInstance();
        }
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}