package com.example.evt_core_service.dto.request;

import com.example.evt_core_service.entity.EventStatus;

import java.util.UUID;

public record UpdateStatusRequest(
        UUID id,
        EventStatus newStatus
) {
}
