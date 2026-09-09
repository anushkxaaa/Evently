package com.evently.service;

import com.evently.common.cdc.CdcChangeHandler;
import com.evently.dto.EventCdcRow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventChangeHandler implements CdcChangeHandler<UUID, EventCdcRow> {

    // e.g. private final EventDocumentRepository repository;
    // e.g. private final EventMapper eventMapper;

    @Override
    public UUID extractId(EventCdcRow row) {
        return UUID.fromString(row.getId());
    }

    @Override
    public void upsert(EventCdcRow row) {
        // var document = eventMapper.toDocument(row);
        // repository.save(document);
        log.info("Upserting event: {}", row.getId());
    }

    @Override
    public void delete(UUID id) {
        // repository.deleteById(id.toString());
        log.info("Deleting event: {}", id);
    }
}