package com.evently.dto.Response;

import java.util.List;

public record EventListResponse(
        List<EventResponse> events
) {
}
