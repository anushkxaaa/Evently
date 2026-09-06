package com.evently.dto.Request;

import jakarta.validation.constraints.NotNull;

public record UpdateStatus(
        @NotNull String newStatus
) {
}
