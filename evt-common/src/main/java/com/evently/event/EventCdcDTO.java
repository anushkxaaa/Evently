package com.evently.event;

import com.evently.common.cdc.CdcRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventCdcDTO implements CdcRecord {

    @JsonProperty("event_id")
    private UUID eventId;

    private String title;
    private String description;
    private String category;

    @JsonProperty("start_time")
    private Long startTime;

    @JsonProperty("end_time")
    private Long endTime;

    private String venue;

    @JsonProperty("organizer_id")
    private UUID organizerId;

    private Integer capacity;
    private BigDecimal price;
    private String status;
    private Long version;
}