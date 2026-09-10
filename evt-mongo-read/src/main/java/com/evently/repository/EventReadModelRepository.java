package com.evently.repository;

import com.evently.document.EventReadModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class EventReadModelRepository {

    private final MongoTemplate mongoTemplate;

    public EventReadModelRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void upsert(EventReadModel event) {
        mongoTemplate.save(event);
    }

    public EventReadModel findById(UUID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, EventReadModel.class);
    }
    public void deleteById(UUID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, EventReadModel.class);
    }

}