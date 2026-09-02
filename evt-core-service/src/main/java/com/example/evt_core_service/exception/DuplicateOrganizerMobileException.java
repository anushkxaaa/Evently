package com.example.evt_core_service.exception;

public class DuplicateOrganizerMobileException extends RuntimeException {
    public DuplicateOrganizerMobileException(String mobile) {
        super("An event with organizer mobile"+mobile+"already exists");
    }
}
