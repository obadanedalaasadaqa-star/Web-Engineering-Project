package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Event> filter(List<Event> events, Map<String, String> params) {
        String title = params.get("title");
        if (title == null || title.trim().isEmpty()) return events;
        String lower = title.toLowerCase();
        return events.stream()
            .filter(e -> e.getTitle().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }
}
