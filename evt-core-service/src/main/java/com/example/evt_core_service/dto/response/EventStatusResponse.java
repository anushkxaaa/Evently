package com.example.evt_core_service.dto.response;

import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.entity.EventStatus;

import java.util.Map;

public record EventStatusResponse(long totalStatus, Map<EventStatus,Long> byStatus,Map<EventCategory,Long> byCategory) {
}
