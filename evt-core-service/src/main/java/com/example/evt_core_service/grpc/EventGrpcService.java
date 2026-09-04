package com.example.evt_core_service.grpc;

import com.example.evt_core_service.dto.request.UpdateStatusRequest;
import com.example.evt_core_service.dto.response.EventResponse;
import com.example.evt_core_service.dto.response.EventStatusResponse;
import com.example.evt_core_service.exception.DuplicateOrganizerMobileException;
import com.example.evt_core_service.exception.EventNotFoundException;
import com.example.evt_core_service.exception.IllegalStatusTransitionException;
import com.example.evt_core_service.service.EventService;
import com.evently.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

@GrpcService
public class EventGrpcService extends EventServiceGrpc.EventServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(EventGrpcService.class);

    private final EventService eventService;

    public EventGrpcService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void createEvent(CreateEventRequest request, StreamObserver<CreateEventResponse> responseObserver) {
        try {
            UUID organizerId = UUID.fromString(request.getOrganizerId());

            com.example.evt_core_service.dto.request.CreateEventRequest coreRequest =
                    new com.example.evt_core_service.dto.request.CreateEventRequest(
                            request.getEventName(),
                            organizerId,
                            request.getOrganizerName(),
                            request.getOrganizerMobile(),
                            request.getCity(),
                            EventEnumMapper.toJpa(request.getCategory())
                    );

            EventResponse event = eventService.createEvent(coreRequest);
            log.info("Created event {}", event.id());

            CreateEventResponse response = CreateEventResponse.newBuilder()
                    .setEvent(EventProtoMapper.toProto(event))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (DuplicateOrganizerMobileException e) {
            responseObserver.onError(Status.ALREADY_EXISTS
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error in createEvent", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }

    @Override
    public void getEvent(GetEventRequest request, StreamObserver<GetEventResponse> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            EventResponse event = eventService.getEvent(id);

            GetEventResponse response = GetEventResponse.newBuilder()
                    .setEvent(EventProtoMapper.toProto(event))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (EventNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Invalid event id format")
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error in getEvent", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }

    @Override
    public void listEvents(ListEventsRequest request, StreamObserver<ListEventsResponse> responseObserver) {
        try {
            String city = request.getCity().isBlank() ? null : request.getCity();
            var category = request.getCategory() == EventCategory.EVENT_CATEGORY_UNSPECIFIED
                    ? null : EventEnumMapper.toJpa(request.getCategory());
            var status = request.getStatus() == com.evently.grpc.EventStatus.EVENT_STATUS_UNSPECIFIED
                    ? null : EventEnumMapper.toJpa(request.getStatus());

            int page = Math.max(request.getPage(), 0);
            int size = request.getSize() > 0 ? request.getSize() : 20;
            Pageable pageable = PageRequest.of(page, size);

            Page<EventResponse> results = eventService.searchEvents(city, category, status, request.getIncludeCancelled(), pageable);

            ListEventsResponse.Builder responseBuilder = ListEventsResponse.newBuilder()
                    .setTotalElements(results.getTotalElements())
                    .setTotalPages(results.getTotalPages());

            results.getContent().forEach(event -> responseBuilder.addEvents(EventProtoMapper.toProto(event)));

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("Unexpected error in listEvents", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }

    @Override
    public void updateEventStatus(UpdateEventStatusRequest request, StreamObserver<UpdateEventStatusResponse> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            var newStatus = EventEnumMapper.toJpa(request.getNewStatus());

            UpdateStatusRequest coreRequest = new UpdateStatusRequest(id, newStatus);
            EventResponse event = eventService.updateStatus(coreRequest);

            UpdateEventStatusResponse response = UpdateEventStatusResponse.newBuilder()
                    .setEvent(EventProtoMapper.toProto(event))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (EventNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (IllegalStatusTransitionException | IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error in updateEventStatus", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }
    @Override
    public void getEventStats(GetEventStatsRequest request, StreamObserver<GetEventStatsResponse> responseObserver) {
        try {
            EventStatusResponse stats = eventService.getStats();

            GetEventStatsResponse.Builder responseBuilder = GetEventStatsResponse.newBuilder()
                    .setTotalEvents(stats.totalStatus());

            stats.byStatus().forEach((status, count) -> responseBuilder.addByStatus(
                    StatusCount.newBuilder()
                            .setStatus(EventEnumMapper.toProto(status))
                            .setCount(count)
                            .build()));

            stats.byCategory().forEach((category, count) -> responseBuilder.addByCategory(
                    CategoryCount.newBuilder()
                            .setCategory(EventEnumMapper.toProto(category))
                            .setCount(count)
                            .build()));

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("Unexpected error in getEventStats", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }
}