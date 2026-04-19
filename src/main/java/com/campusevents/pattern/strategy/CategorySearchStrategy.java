package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategorySearchStrategy implements SearchStrategy {
    @Override
    public List<Event> filter(List<Event> events, Map<String, String> params) {
        String categoryId = params.get("category_id");
        if (categoryId == null || categoryId.trim().isEmpty()) return events;
        return events.stream()
            .filter(e -> categoryId.equals(e.getCategoryId()))
            .collect(Collectors.toList());
    }
}
