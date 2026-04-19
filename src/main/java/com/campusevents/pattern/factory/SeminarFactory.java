package com.campusevents.pattern.factory;

import com.campusevents.model.Event;
import java.time.OffsetDateTime;

public class SeminarFactory implements EventFactory {
    @Override
    public Event create(String title, String description, String organizerId,
                        String categoryId, String dateTime, String location,
                        int capacity, String department) {
        Event e = new Event();
        e.setType("seminar");
        e.setTitle(title);
        e.setDescription(description);
        e.setOrganizerId(organizerId);
        e.setCategoryId(categoryId);
        e.setDateTime(OffsetDateTime.parse(dateTime));
        e.setLocation(location);
        e.setCapacity(capacity);
        e.setSeatsRemaining(capacity);
        e.setDepartment(department);
        e.setStatus("open");
        return e;
    }
}
