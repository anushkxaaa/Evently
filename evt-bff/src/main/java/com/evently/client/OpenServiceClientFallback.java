package com.evently.client;

import com.evently.dto.request.CreateEventRequest;
import com.evently.dto.response.EventResponse;
import com.evently.exception.OpenServiceUnavailableException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OpenServiceClientFallback implements OpenServiceClient {

    @Override
    public EventResponse createEvent(CreateEventRequest request){
        throw new OpenServiceUnavailableException("Open service is Not available");
    }

    @Override
    public EventResponse getEvent(UUID id) {
        throw new OpenServiceUnavailableException("open-service is currently unavailable");
    }

    @Override
    public List<EventResponse> searchEvents(String city, String category, String status,
                                            boolean includeCancelled, int page, int size) {
        throw new OpenServiceUnavailableException("open-service is currently unavailable");
    }
}
