package com.evently.client;


import com.evently.dto.request.CreateEventRequest;
import com.evently.dto.response.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name="evt-open-service", url="${evt-open-service.url}")
public interface OpenServiceClient {
    @PostMapping("/open/v1/events")
    EventResponse createEvent(@RequestBody CreateEventRequest request);

    @GetMapping("/open/v1/events/{id}")
    EventResponse getEvent(@PathVariable("id") UUID id);

    @GetMapping("/open/v1/events")
    List<EventResponse> searchEvents(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "false") boolean includeCancelled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size);
}
