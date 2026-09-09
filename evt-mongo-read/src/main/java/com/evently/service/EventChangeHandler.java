package com.evently.service;

import com.evently.common.cdc.CdcChangeHandler;
import com.evently.document.EventReadModel;
import com.evently.dto.EventCdcRow;
import com.evently.mapper.EventCdcRowMapper;
import com.evently.repository.EventReadModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventChangeHandler implements CdcChangeHandler<UUID, EventCdcRow> {

    private final EventCdcRowMapper mapper;
    private final EventReadModelRepository repository;

    @Override
    public UUID extractId(EventCdcRow row) {
        return UUID.fromString(row.getId());
    }

    @Override
    public void upsert(EventCdcRow row) {
        EventReadModel document = mapper.toReadModel(row);
        repository.upsert(document);
        log.info("Upserted event: {}", row.getId());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
        log.info("Deleted event: {}", id);
    }
}