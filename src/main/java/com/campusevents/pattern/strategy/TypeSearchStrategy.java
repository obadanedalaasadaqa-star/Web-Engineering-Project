package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeSearchStrategy implements SearchStrategy {
    @Override
    public List<Event> filter(List<Event> events, Map<String, String> params) {
        String type = params.get("type");
        if (type == null || type.trim().isEmpty()) return events;
        return events.stream()
            .filter(e -> type.equals(e.getType()))
            .collect(Collectors.toList());
    }
}
