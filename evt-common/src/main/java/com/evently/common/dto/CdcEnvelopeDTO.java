package com.evently.common.dto;

import com.evently.common.cdc.CdcRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CdcEnvelopeDTO<T extends CdcRecord> {
    private CdcEventPayload<T> payload;
}