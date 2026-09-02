package com.example.evt_core_service.repository;

import com.example.evt_core_service.entity.Event;
import com.example.evt_core_service.entity.EventCategory;
import com.example.evt_core_service.entity.EventStatus;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {

    private EventSpecifications(){

    }

    public static Specification<Event> hasCity(String city){
        return(root,query,cb)->city==null?null:cb.equal(root.get("city"),city);
    }
    public static Specification<Event> hasCategory(EventCategory category) {
        return (root, query, cb) ->
                category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<Event> hasStatus(EventStatus status){
        return(root,query,cb)->
                status==null?null:cb.equal(root.get("status"),status);
    }

    public static Specification<Event> excludeCancelled(){
        return(root,query,cb)->
                cb.notEqual(root.get("status"), EventStatus.CANCELLED);
    }
}
