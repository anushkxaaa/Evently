package com.evently.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CdcSourceDTO {

    private String version;
    private String connector;
    private String name;

    @JsonProperty("ts_ms")
    private Long tsMs;

    private String snapshot;
    private String db;
    private String sequence;

    @JsonProperty("ts_us")
    private Long tsUs;

    @JsonProperty("ts_ns")
    private Long tsNs;

    private String schema;
    private String table;

    @JsonProperty("txId")
    private Long txId;

    private Long lsn;
    private Long xmin;
}