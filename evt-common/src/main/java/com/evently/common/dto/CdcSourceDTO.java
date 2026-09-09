package com.evently.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CdcSourceDTO {
    private String version;
    private String connector;
    private String name;
    private Long tsMs;
    private String snapshot;
    private String db;
    private String sequence;
    private String schema;
    private String table;
    private Long txId;
    private Long lsn;
    private Long xmin;
}