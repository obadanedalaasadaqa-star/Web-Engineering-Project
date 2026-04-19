package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DateSearchStrategy implements SearchStrategy {
    @Override
    public List<Event> filter(List<Event> events, Map<String, String> params) {
        String date = params.get("date");
        if (date == null || date.trim().isEmpty()) return events;
        LocalDate target = LocalDate.parse(date);
        return events.stream()
            .filter(e -> e.getDateTime() != null &&
                         e.getDateTime().toLocalDate().equals(target))
            .collect(Collectors.toList());
    }
}
