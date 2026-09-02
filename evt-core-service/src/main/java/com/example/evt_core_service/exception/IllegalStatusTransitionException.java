package com.example.evt_core_service.exception;

import com.example.evt_core_service.entity.EventStatus;

public class IllegalStatusTransitionException extends RuntimeException {
    public IllegalStatusTransitionException(EventStatus from,EventStatus to) {
        super("Cannot transititon event from"+from+"to"+to);
    }
}
