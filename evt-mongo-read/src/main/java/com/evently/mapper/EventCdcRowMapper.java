package com.evently.mapper;

import com.evently.document.EventReadModel;
import com.evently.dto.EventCdcRow;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class EventCdcRowMapper {

    public EventReadModel toReadModel(EventCdcRow row) {
        EventReadModel doc = new EventReadModel();
        doc.setId(UUID.fromString(row.getId()));
        doc.setEventName(row.getEventName());
        doc.setOrganizerId(UUID.fromString(row.getOrganizerId()));
        doc.setOrganizerName(row.getOrganizerName());
        doc.setOrganizerMobile(row.getOrganizerMobile());
        doc.setCity(row.getCity());
        doc.setCategory(row.getCategory());
        doc.setStatus(row.getStatus());
        doc.setBannerImageKey(row.getBannerImageKey());
        doc.setCreatedOn(toInstant(row.getCreatedOn()));
        doc.setModifiedOn(toInstant(row.getModifiedOn()));
        return doc;
    }

    private Instant toInstant(String zonedTimestamp) {
        if (zonedTimestamp == null) return null;
        return ZonedDateTime.parse(zonedTimestamp).toInstant();
    }
}