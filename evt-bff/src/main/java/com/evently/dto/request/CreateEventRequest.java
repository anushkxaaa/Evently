package com.evently.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateEventRequest (@NotNull String eventName,
                                 @NotNull UUID organizerId,
                                 @NotBlank String organizerName,
                                 @NotBlank @Pattern(regexp="\\d{10}", message="enter 10 digits")  String organizerMobile,
                                 @NotBlank String city,
                                 @NotBlank String category){
}
