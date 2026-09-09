package com.evently.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document(collection = "events")
public class EventReadModel {

    @Id
    private UUID id;

    private String eventName;
    private UUID organizerId;
    private String organizerName;
    private String organizerMobile;
    private String city;
    private String category;
    private String status;
    private String bannerImageKey;
    private Instant createdOn;
    private Instant modifiedOn;
}