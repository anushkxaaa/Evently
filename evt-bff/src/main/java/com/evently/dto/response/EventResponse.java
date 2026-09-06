package com.evently.dto.response;

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
        String createdOn,
        String modifiedOn
) {
}
