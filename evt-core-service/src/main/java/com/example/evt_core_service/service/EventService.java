package com.example.evt_core_service.service;

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
    public Event createEvent(String eventName, UUID organizerId, String organizerName,
                             String organizerMobile, String city, EventCategory category) {
        if (eventRepository.existsByOrganizerMobile(organizerMobile)) {
            throw new DuplicateOrganizerMobileException(organizerMobile);
        }

        Event event = new Event(eventName, organizerId, organizerName, organizerMobile, city, category);

        try {
            return eventRepository.save(event);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateOrganizerMobileException(organizerMobile);
        }
    }

    @Transactional(readOnly = true)
    public Event getEvent(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<Event> searchEvents(String city, EventCategory category, EventStatus status,
                                    boolean includeCancelled, Pageable pageable) {
        Specification<Event> spec = Specification.where(EventSpecifications.hasCity(city))
                .and(EventSpecifications.hasCategory(category))
                .and(EventSpecifications.hasStatus(status));

        if (!includeCancelled) {
            spec = spec.and(EventSpecifications.excludeCancelled());
        }

        return eventRepository.findAll(spec, pageable);
    }

    @Transactional
    public Event updateStatus(UUID id, EventStatus newStatus) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        EventStatus currentStatus = event.getStatus();
        Set<EventStatus> allowed = ALLOWED_TRANSITIONS.getOrDefault(currentStatus, EnumSet.noneOf(EventStatus.class));

        if (!allowed.contains(newStatus)) {
            throw new IllegalStatusTransitionException(currentStatus, newStatus);
        }

        event.transitionStatusTo(newStatus);
        return event;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStats() {
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

        return Map.of(
                "totalEvents", totalEvents,
                "byStatus", byStatus,
                "byCategory", byCategory
        );
    }
}