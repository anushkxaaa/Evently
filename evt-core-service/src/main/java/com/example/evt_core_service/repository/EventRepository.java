package com.example.evt_core_service.repository;

import com.example.evt_core_service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {
    boolean existsByOrganizerMobile(String organizerMobile);
    Optional<Event> findByOrganizerMobile(String organizerMobile);
}
