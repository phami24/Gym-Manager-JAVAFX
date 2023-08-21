package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Event;
import com.example.gymmanagement.model.repository.EventRepository;
import com.example.gymmanagement.model.service.EventService;
import java.util.List;

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl() {
        this.eventRepository = new EventRepository();
    }

    @Override
    public void addEvent(Event event) {
        eventRepository.addEvent(event);
    }

    @Override
    public void updateEvent(Event event) {
        eventRepository.updateEvent(event);
    }

    @Override
    public void deleteEvent(String eventId) {
        eventRepository.deleteEvent(eventId);
    }

    @Override
    public Event getEventById(int eventId) {
        return eventRepository.getEventById(eventId);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    // Additional methods related to event functionality can be added here
}
