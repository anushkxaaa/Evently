package com.evently.mapper;

import com.evently.document.EventReadModel;
import com.evently.dto.EventReadResponse;
import org.springframework.stereotype.Component;

@Component
public class EventReadResponseMapper {

    public EventReadResponse toResponse(EventReadModel document) {
        return new EventReadResponse(
                document.getId(),
                document.getEventName(),
                document.getOrganizerId(),
                document.getOrganizerName(),
                document.getOrganizerMobile(),
                document.getCity(),
                document.getCategory(),
                document.getStatus(),
                document.getBannerImageKey(),
                document.getCreatedOn(),
                document.getModifiedOn()
        );
    }
}