package com.evently.common.dto;

import com.evently.common.cdc.CdcRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebeziumMessage<T extends CdcRecord> {

    private Object schema;
    private CdcEventPayload<T> payload;
}