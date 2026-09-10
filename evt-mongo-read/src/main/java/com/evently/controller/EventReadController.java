package com.evently.controller;

import com.evently.document.EventReadModel;
import com.evently.dto.EventReadResponse;
import com.evently.mapper.EventReadResponseMapper;
import com.evently.repository.EventReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/read/v1/events")
@RequiredArgsConstructor
public class EventReadController {

    private final EventReadModelRepository repository;
    private final EventReadResponseMapper mapper;

    @GetMapping("/{id}")
    public EventReadResponse getEvent(@PathVariable UUID id) {
        EventReadModel document = repository.findById(id);
        return mapper.toResponse(document);
    }


}