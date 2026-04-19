package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentSearchStrategy implements SearchStrategy {
    @Override
    public List<Event> filter(List<Event> events, Map<String, String> params) {
        String dept = params.get("department");
        if (dept == null || dept.trim().isEmpty()) return events;
        String lower = dept.toLowerCase();
        return events.stream()
            .filter(e -> e.getDepartment() != null && e.getDepartment().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }
}
