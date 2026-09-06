package com.evently.controller;

import com.evently.dto.Response.EventResponse;
import com.evently.dto.Request.CreateEventRequest;
import com.evently.grpc.*;
import com.evently.mapper.EventProtoToDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/open/v1/events")
public class EventController {
    private final EventGrpcClient eventGrpcClient;

    public EventController(EventGrpcClient eventGrpcClient){
        this.eventGrpcClient=eventGrpcClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable("id")  UUID id){
        GetEventRequest grpcRequest= GetEventRequest.newBuilder().setId(id.toString()).build();
        GetEventResponse grpcResponse = eventGrpcClient.getEvent(grpcRequest);
        return ResponseEntity.ok(EventProtoToDtoMapper.toDto(grpcResponse.getEvent()));
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody CreateEventRequest request) {
        com.evently.grpc.CreateEventRequest grpcRequest = com.evently.grpc.CreateEventRequest.newBuilder()
                .setEventName(request.eventName())
                .setOrganizerId(request.organizerId().toString())
                .setOrganizerName(request.organizerName())
                .setOrganizerMobile(request.organizerMobile())
                .setCity(request.city())
                .setCategory(request.category())
                .build();

        CreateEventResponse grpcResponse = eventGrpcClient.createEvent(grpcRequest);
        EventResponse response = EventProtoToDtoMapper.toDto(grpcResponse.getEvent());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




}
