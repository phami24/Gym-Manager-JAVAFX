package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Event;

import java.math.BigDecimal;
import java.util.List;

public interface EventService {
    void addEvent(Event event);

    void updateEvent(Event event);

    void deleteEvent(String eventId);

    void deleteEvent(int eventId);

    Event getEventById(int eventId);

    List<Event> getAllEvents();

    // Additional methods related to event functionality can be added here
}
