package com.evently.grpc;

import lombok.*;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventGrpcClient {
    private final EventServiceGrpc.EventServiceBlockingStub stub;

    public CreateEventResponse createEvent(CreateEventRequest request) {
        return stub.createEvent(request);
    }

    public GetEventResponse getEvent(GetEventRequest request) {
        return stub.getEvent(request);
    }

    public ListEventsResponse listEvents(ListEventsRequest request) {
        return stub.listEvents(request);
    }

    public UpdateEventStatusResponse updateEventStatus(UpdateEventStatusRequest request) {
        return stub.updateEventStatus(request);
    }

    public GetEventStatsResponse getEventStats(GetEventStatsRequest request) {
        return stub.getEventStats(request);
    }
}