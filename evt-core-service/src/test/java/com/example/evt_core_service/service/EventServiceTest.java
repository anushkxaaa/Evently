package com.example.evt_core_service.service;

import com.example.evt_core_service.dto.request.CreateEventRequest;
import com.example.evt_core_service.dto.response.EventResponse;
import com.example.evt_core_service.entity.Event;
import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.exception.DuplicateOrganizerMobileException;
import com.example.evt_core_service.exception.EventNotFoundException;
import com.example.evt_core_service.repository.EventRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    private CreateEventRequest createRequest;

    @BeforeEach
    void setup() {

//        eventRepository.deleteAll();

        createRequest = new CreateEventRequest(
                "Adgrid Event",
                UUID.randomUUID(),
                "Anushka",
                "9999999999",
                "Delhi",
                EventCategory.values()[0]
        );
    }

//    @Test
//    void checkTimezone() {
//        System.out.println("JVM timezone = " + java.util.TimeZone.getDefault().getID());
//        System.out.println("ZoneId = " + java.time.ZoneId.systemDefault());
//    }

    @Nested
    class CreateEvent {

        @Test
        void createEventWhenNotDuplicate() {

            when(eventRepository.existsByOrganizerMobile("9999999999")).thenReturn(false);
            Event event = new Event(
                    createRequest.eventName(),
                    createRequest.organizerId(),
                    createRequest.organizerName(),
                    createRequest.organizerMobile(),
                    createRequest.city(),
                    createRequest.category()
            );
            when(eventRepository.save(any(Event.class))).thenReturn(event);
            EventResponse response = eventService.createEvent(createRequest);
            assertNotNull(response);
            assertEquals("Adgrid Event", response.eventName());
            assertEquals("Anushka", response.organizerName());
            verify(eventRepository).existsByOrganizerMobile("9999999999");
            ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
            verify(eventRepository).save(captor.capture());
            Event captureEvent =captor.getValue();
            assertEquals("Adgrid Event", captureEvent.getEventName());
            assertEquals("Anushka",captureEvent.getOrganizerName());

        }

        @Test
        void throwDuplicateMobileException() {

            when(eventRepository.existsByOrganizerMobile("9999999999")).thenReturn(true);
            assertThrows(DuplicateOrganizerMobileException.class, ()->eventService.createEvent(createRequest));
            verify(eventRepository).existsByOrganizerMobile("9999999999");

        }
    }

    @Nested
    class GetEvent {

        @Test
        void whenFound() {

            UUID id = UUID.randomUUID();
            Event event = new Event(
                    "Adgid Event",
                    UUID.randomUUID(),
                    "Anushka",
                    "9999999999",
                    "Delhi",
                    EventCategory.values()[0]
            );
            when(eventRepository.findById(id)).thenReturn(Optional.of(event));

            EventResponse response = eventService.getEvent(id);

            assertNotNull(response);
            verify(eventRepository).findById(id);
        }

        @Test
        void whenNotFound() {

            UUID id = UUID.randomUUID();
            when(eventRepository.findById(id)).thenReturn(Optional.empty());
            assertThrows(EventNotFoundException.class, () -> eventService.getEvent(id));
            verify(eventRepository).findById(id);
        }
    }
}