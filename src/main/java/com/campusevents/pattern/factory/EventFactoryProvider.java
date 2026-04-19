package com.campusevents.pattern.factory;

public class EventFactoryProvider {

    public static EventFactory getFactory(String type) {
        switch (type) {
            case "workshop":        return new WorkshopFactory();
            case "seminar":         return new SeminarFactory();
            case "club_social":     return new ClubSocialFactory();
            case "sports_activity": return new SportsActivityFactory();
            default: throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}
