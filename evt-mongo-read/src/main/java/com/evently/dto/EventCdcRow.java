package com.evently.dto;

import com.evently.common.cdc.CdcRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventCdcRow implements CdcRecord, Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("created_on")
    private String createdOn;

    @JsonProperty("modified_on")
    private String modifiedOn;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("organizer_id")
    private String organizerId;

    @JsonProperty("organizer_name")
    private String organizerName;

    @JsonProperty("organizer_mobile")
    private String organizerMobile;

    @JsonProperty("city")
    private String city;

    @JsonProperty("category")
    private String category;

    @JsonProperty("status")
    private String status;

    @JsonProperty("banner_image_key")
    private String bannerImageKey;
}