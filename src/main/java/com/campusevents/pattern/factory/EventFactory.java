package com.campusevents.pattern.factory;

import com.campusevents.model.Event;

public interface EventFactory {
    Event create(String title, String description, String organizerId,
                 String categoryId, String dateTime, String location,
                 int capacity, String department);
}
