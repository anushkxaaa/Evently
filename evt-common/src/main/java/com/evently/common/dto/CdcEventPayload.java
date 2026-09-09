package com.evently.common.dto;

import com.evently.common.cdc.CdcRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CdcEventPayload<T extends CdcRecord> {
    private String op;
    private T before;
    private T after;
    private CdcSourceDTO source;

    public boolean isDelete() {
        return "d".equals(op);
    }

    public boolean isSnapshot() {
        return "r".equals(op);
    }

    public T currentState() {
        return isDelete() ? before : after;
    }
}