package com.evently.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull String newStatus
) {
}
