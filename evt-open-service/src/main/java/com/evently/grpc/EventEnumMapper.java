package com.evently.grpc;


public final class EventEnumMapper {

    private EventEnumMapper() {
    }

    public static EventCategory toProtoCategory(String category) {
        if (category == null) return EventCategory.EVENT_CATEGORY_UNSPECIFIED;
        return switch (category.toUpperCase()) {
            case "MUSIC" -> EventCategory.EVENT_CATEGORY_MUSIC;
            case "SPORTS" -> EventCategory.EVENT_CATEGORY_SPORTS;
            case "COMEDY" -> EventCategory.EVENT_CATEGORY_COMEDY;
            case "WORKSHOP" -> EventCategory.EVENT_CATEGORY_WORKSHOP;
            case "OTHER" -> EventCategory.EVENT_CATEGORY_OTHER;
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }

    public static EventStatus toProtoStatus(String status) {
        if (status == null) return EventStatus.EVENT_STATUS_UNSPECIFIED;
        return switch (status.toUpperCase()) {
            case "DRAFT" -> EventStatus.EVENT_STATUS_DRAFT;
            case "PUBLISHED" -> EventStatus.EVENT_STATUS_PUBLISHED;
            case "CANCELLED" -> EventStatus.EVENT_STATUS_CANCELLED;
            case "SOLD_OUT" -> EventStatus.EVENT_STATUS_SOLD_OUT;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }

    public static String fromProtoCategory(EventCategory category) {
        return category.name();
    }

    public static String fromProtoStatus(EventStatus status) {
        return status.name();
    }
}