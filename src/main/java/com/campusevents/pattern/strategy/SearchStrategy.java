package com.campusevents.pattern.strategy;

import com.campusevents.model.Event;
import java.util.List;
import java.util.Map;

public interface SearchStrategy {
    List<Event> filter(List<Event> events, Map<String, String> params);
}
