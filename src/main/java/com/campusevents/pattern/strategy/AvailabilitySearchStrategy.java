package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AvailabilitySearchStrategy implements SearchStrategy {
    @Override
    public List<Event> filter(List<Event> events, Map<String, String> params) {
        String available = params.get("available");
        if (!"true".equals(available)) return events;
        return events.stream()
            .filter(e -> e.getSeatsRemaining() > 0)
            .collect(Collectors.toList());
    }
}
