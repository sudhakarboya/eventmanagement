/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.eventmanagementsystem.controller;

import java.util.*;
import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.service.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class EventController {
    @Autowired
    private EventJpaService eventJpaService;

    @GetMapping("/events")
    public ArrayList<Event> getEvents() {
        return eventJpaService.getEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable("eventId") int eventId) {
        return eventJpaService.getEventById(eventId);
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) {
        return eventJpaService.addEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable("eventId") int eventId, @RequestBody Event event) {
        return eventJpaService.updateEvent(eventId, event);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable("eventId") int eventId) {
        eventJpaService.deleteEvent(eventId);
    }

    @GetMapping("/events/{eventId}/sponsors")
    public List<Sponsor> getEventSponsors(@PathVariable("eventId") int eventId) {
        return eventJpaService.getEventSponsors(eventId);
    }

}
