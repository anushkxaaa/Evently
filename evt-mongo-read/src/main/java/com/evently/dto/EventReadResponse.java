package com.evently.dto;

import java.time.Instant;
import java.util.UUID;

public record EventReadResponse(
        UUID id,
        String eventName,
        UUID organizerId,
        String organizerName,
        String organizerMobile,
        String city,
        String category,
        String status,
        String bannerImageKey,
        Instant createdOn,
        Instant modifiedOn
) {
}