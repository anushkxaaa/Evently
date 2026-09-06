package com.evently.dto.Response;

import java.time.Instant;
import java.util.UUID;

public record EventResponse(
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
