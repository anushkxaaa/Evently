package com.example.evt_core_service.grpc;

import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.entity.EventStatus;

public class EventEnumMapper {

    private EventEnumMapper() {
    }

    // Category: JPA -> Proto
    public static com.evently.grpc.EventCategory toProto(EventCategory jpaCategory) {
        if (jpaCategory == null) {
            return com.evently.grpc.EventCategory.EVENT_CATEGORY_UNSPECIFIED;
        }
        return switch (jpaCategory) {
            case MUSIC -> com.evently.grpc.EventCategory.EVENT_CATEGORY_MUSIC;
            case SPORTS -> com.evently.grpc.EventCategory.EVENT_CATEGORY_SPORTS;
            case COMEDY -> com.evently.grpc.EventCategory.EVENT_CATEGORY_COMEDY;
            case WORKSHOP -> com.evently.grpc.EventCategory.EVENT_CATEGORY_WORKSHOP;
            case OTHER -> com.evently.grpc.EventCategory.EVENT_CATEGORY_OTHER;
        };
    }

    // ---------- Category: Proto -> JPA ----------
    public static EventCategory toJpa(com.evently.grpc.EventCategory protoCategory) {
        return switch (protoCategory) {
            case EVENT_CATEGORY_MUSIC -> EventCategory.MUSIC;
            case EVENT_CATEGORY_SPORTS -> EventCategory.SPORTS;
            case EVENT_CATEGORY_COMEDY -> EventCategory.COMEDY;
            case EVENT_CATEGORY_WORKSHOP -> EventCategory.WORKSHOP;
            case EVENT_CATEGORY_OTHER -> EventCategory.OTHER;
            case EVENT_CATEGORY_UNSPECIFIED, UNRECOGNIZED ->
                    throw new IllegalArgumentException("EventCategory must be specified");
        };
    }

    // ---------- Status: JPA -> Proto ----------
    public static com.evently.grpc.EventStatus toProto(EventStatus jpaStatus) {
        if (jpaStatus == null) {
            return com.evently.grpc.EventStatus.EVENT_STATUS_UNSPECIFIED;
        }
        return switch (jpaStatus) {
            case DRAFT -> com.evently.grpc.EventStatus.EVENT_STATUS_DRAFT;
            case PUBLISHED -> com.evently.grpc.EventStatus.EVENT_STATUS_PUBLISHED;
            case CANCELLED -> com.evently.grpc.EventStatus.EVENT_STATUS_CANCELLED;
            case SOLD_OUT -> com.evently.grpc.EventStatus.EVENT_STATUS_SOLD_OUT;
        };
    }

    // ---------- Status: Proto -> JPA ----------
    public static EventStatus toJpa(com.evently.grpc.EventStatus protoStatus) {
        return switch (protoStatus) {
            case EVENT_STATUS_DRAFT -> EventStatus.DRAFT;
            case EVENT_STATUS_PUBLISHED -> EventStatus.PUBLISHED;
            case EVENT_STATUS_CANCELLED -> EventStatus.CANCELLED;
            case EVENT_STATUS_SOLD_OUT -> EventStatus.SOLD_OUT;
            case EVENT_STATUS_UNSPECIFIED, UNRECOGNIZED ->
                    throw new IllegalArgumentException("EventStatus must be specified");
        };
    }
}