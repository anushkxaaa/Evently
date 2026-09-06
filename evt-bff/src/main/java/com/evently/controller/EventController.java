package com.evently.controller;

import com.evently.client.OpenServiceClient;
import com.evently.dto.request.CreateEventRequest;
import com.evently.dto.response.EventResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final OpenServiceClient openServiceClient;

    public EventController(OpenServiceClient openServiceClient){
        this.openServiceClient=openServiceClient;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody CreateEventRequest request){
        EventResponse created= openServiceClient.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable("id") UUID id){
        return ResponseEntity.ok(openServiceClient.getEvent(id));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> searchEvents(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "false") boolean includeCancelled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(openServiceClient.searchEvents(city, category, status, includeCancelled, page, size));
    }
}
