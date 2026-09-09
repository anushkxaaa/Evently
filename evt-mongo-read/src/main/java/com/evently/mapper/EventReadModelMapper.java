package com.evently.mapper;

import com.evently.document.EventReadModel;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class EventReadModelMapper {

    public EventReadModel toReadModel(JsonNode node) {
        EventReadModel event = new EventReadModel();
        event.setId(
                UUID.fromString(node.get("id").asText())
        );
        event.setEventName(
                node.get("event_name").asText()
        );
        event.setOrganizerId(
                UUID.fromString(node.get("organizer_id").asText())
        );

        event.setOrganizerName(
                node.get("organizer_name").asText()
        );

        event.setOrganizerMobile(
                node.get("organizer_mobile").asText()
        );

        event.setCity(
                node.get("city").asText()
        );

        event.setCategory(
                node.get("category").asText()
        );

        event.setStatus(
                node.get("status").asText()
        );

        if (!node.get("banner_image_key").isNull()) {
            event.setBannerImageKey(
                    node.get("banner_image_key").asText()
            );
        }

        event.setCreatedOn(
                Instant.parse(node.get("created_on").asText())
        );

        event.setModifiedOn(
                Instant.parse(node.get("modified_on").asText())
        );
        return event;
    }
}