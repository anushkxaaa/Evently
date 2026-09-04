package com.example.evt_core_service.service;

import com.example.evt_core_service.dto.request.CreateEventRequest;
import com.example.evt_core_service.dto.request.UpdateStatusRequest;
import com.example.evt_core_service.dto.response.EventStatusResponse;
import com.example.evt_core_service.dto.response.EventResponse;
import com.example.evt_core_service.entity.Event;
import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.entity.EventStatus;
import com.example.evt_core_service.exception.DuplicateOrganizerMobileException;
import com.example.evt_core_service.exception.EventNotFoundException;
import com.example.evt_core_service.exception.IllegalStatusTransitionException;
import com.example.evt_core_service.repository.EventRepository;
import com.example.evt_core_service.repository.EventSpecifications;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private static final Map<EventStatus, Set<EventStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(EventStatus.class);
    static {
        ALLOWED_TRANSITIONS.put(EventStatus.DRAFT, EnumSet.of(EventStatus.PUBLISHED));
        ALLOWED_TRANSITIONS.put(EventStatus.PUBLISHED, EnumSet.of(EventStatus.CANCELLED, EventStatus.SOLD_OUT));
        ALLOWED_TRANSITIONS.put(EventStatus.CANCELLED, EnumSet.noneOf(EventStatus.class));
        ALLOWED_TRANSITIONS.put(EventStatus.SOLD_OUT, EnumSet.noneOf(EventStatus.class));
    }

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        if (eventRepository.existsByOrganizerMobile(request.organizerMobile())) {
            throw new DuplicateOrganizerMobileException(request.organizerMobile());
        }
        Event event = new Event(
                request.eventName(),
                request.organizerId(),
                request.organizerName(),
                request.organizerMobile(),
                request.city(),
                request.category()
        );
        try {
             Event saved=eventRepository.save(event);
             return EventResponse.from(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateOrganizerMobileException(request.organizerMobile());
        }
    }

    @Transactional(readOnly = true)
    public EventResponse getEvent(UUID id) {
        Event event= eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return EventResponse.from(event);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> searchEvents(String city, EventCategory category, EventStatus status,
                                    boolean includeCancelled, Pageable pageable) {
        Specification<Event> spec = Specification.where(EventSpecifications.hasCity(city))
                .and(EventSpecifications.hasCategory(category))
                .and(EventSpecifications.hasStatus(status));

        if (!includeCancelled) {
            spec = spec.and(EventSpecifications.excludeCancelled());
        }

        return eventRepository.findAll(spec, pageable).map(EventResponse::from);
    }

    @Transactional
    public EventResponse updateStatus(UpdateStatusRequest request) {
        Event event = eventRepository.findById(request.id())
                .orElseThrow(() -> new EventNotFoundException(request.id()));

        EventStatus currentStatus = event.getStatus();
        Set<EventStatus> allowed = ALLOWED_TRANSITIONS.getOrDefault(currentStatus, EnumSet.noneOf(EventStatus.class));

        if (!allowed.contains(request.newStatus())) {
            throw new IllegalStatusTransitionException(currentStatus, request.newStatus());
        }

        event.transitionStatusTo(request.newStatus());
        return EventResponse.from(event);
    }

    @Transactional(readOnly = true)
    public EventStatusResponse getStats() {
        long totalEvents = eventRepository.count();

        Map<EventStatus, Long> byStatus = new EnumMap<>(EventStatus.class);
        for (EventStatus status : EventStatus.values()) {
            byStatus.put(status, eventRepository.count(EventSpecifications.hasStatus(status)));
        }

        Map<EventCategory, Long> byCategory = new EnumMap<>(EventCategory.class);
        for (EventCategory category : EventCategory.values()) {
            byCategory.put(category, eventRepository.count(
                    Specification.where(EventSpecifications.hasCategory(category))
                            .and(EventSpecifications.excludeCancelled())));
        }

        return new EventStatusResponse(totalEvents,byStatus,byCategory);
    }
}