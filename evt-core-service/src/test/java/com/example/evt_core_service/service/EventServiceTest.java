package com.example.evt_core_service.service;

import com.example.evt_core_service.dto.request.CreateEventRequest;
import com.example.evt_core_service.dto.response.EventResponse;
import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.exception.DuplicateOrganizerMobileException;
import com.example.evt_core_service.exception.EventNotFoundException;
import com.example.evt_core_service.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    private CreateEventRequest createRequest;

    @BeforeEach
    void setup() {

        eventRepository.deleteAll();

        createRequest = new CreateEventRequest(
                "Adgid Event",
                UUID.randomUUID(),
                "Anushka",
                "9999999999",
                "Delhi",
                EventCategory.values()[0]
        );
    }

    @Test
    void checkTimezone() {
        System.out.println("JVM timezone = " + java.util.TimeZone.getDefault().getID());
        System.out.println("ZoneId = " + java.time.ZoneId.systemDefault());
    }

    @Nested
    class CreateEvent {

        @Test
        void createEventWhenNotDuplicate() {

            EventResponse response =
                    eventService.createEvent(createRequest);

            assertNotNull(response);
            assertEquals("Adgid Event", response.eventName());
            assertEquals("Anushka", response.organizerName());
        }

        @Test
        void throwDuplicateMobileException() {

            eventService.createEvent(createRequest);

            CreateEventRequest duplicate =
                    new CreateEventRequest(
                            "Fun Event",
                            UUID.randomUUID(),
                            "Den",
                            "9999999999",
                            "Delhi",
                            EventCategory.MUSIC
                    );

            assertThrows(
                    DuplicateOrganizerMobileException.class,
                    () -> eventService.createEvent(duplicate)
            );

            assertEquals(1, eventRepository.count());
        }
    }

    @Nested
    class GetEvent {

        @Test
        void whenFound() {

            EventResponse created =
                    eventService.createEvent(createRequest);

            EventResponse fetched =
                    eventService.getEvent(created.id());

            assertNotNull(fetched);
            assertEquals(created.id(), fetched.id());
        }

        @Test
        void whenNotFound() {

            assertThrows(
                    EventNotFoundException.class,
                    () -> eventService.getEvent(UUID.randomUUID())
            );
        }
    }
}