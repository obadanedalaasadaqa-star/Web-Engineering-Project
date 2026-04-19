package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EventSearchContext {

    private final List<SearchStrategy> strategies = Arrays.asList(
        new TitleSearchStrategy(),
        new DepartmentSearchStrategy(),
        new DateSearchStrategy(),
        new CategorySearchStrategy(),
        new AvailabilitySearchStrategy(),
        new TypeSearchStrategy()
    );

    public List<Event> execute(List<Event> events, Map<String, String> params) {
        List<Event> result = events;
        for (SearchStrategy strategy : strategies) {
            result = strategy.filter(result, params);
        }
        return result;
    }
}
