package com.evently.service;

import com.evently.document.EventReadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventReadService {

    private final MongoTemplate mongoTemplate;

    public void save(EventReadModel event) {
        mongoTemplate.save(event);
    }

    public EventReadModel findById(UUID id) {
        return mongoTemplate.findById(id, EventReadModel.class);
    }

    public List<EventReadModel> findAll() {
        return mongoTemplate.findAll(EventReadModel.class);
    }
}