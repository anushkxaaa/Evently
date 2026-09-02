package com.example.evt_core_service.exception;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(UUID id) {
        super("Event with thid id not found"+id);
    }
}
