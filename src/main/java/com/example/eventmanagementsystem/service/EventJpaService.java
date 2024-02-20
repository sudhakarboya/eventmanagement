/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here

package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventJpaService implements EventRepository {
    @Autowired
    private EventJpaRepository eventJpaRepository;
    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Event> getEvents() {
        return (ArrayList<Event>) eventJpaRepository.findAll();
    }

    @Override
    public Event getEventById(int eventId) {
        try {
            return eventJpaRepository.findById(eventId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Event addEvent(Event event) {
        try {
            List<Integer> sponsorIds = new ArrayList<>();
            for (Sponsor sponsor : event.getSponsors()) {
                sponsorIds.add(sponsor.getSponsorId());
            }
            List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
            if (sponsorIds.size() != sponsors.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some sponsors not available");
            }
            event.setSponsors(sponsors);
            return eventJpaRepository.save(event);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        try {
            Event existEvent = eventJpaRepository.findById(eventId).get();
            if (event.getEventName() != null) {
                existEvent.setEventName(event.getEventName());
            }
            if (event.getDate() != null) {
                existEvent.setDate(event.getDate());
            }
            if (event.getSponsors() != null) {
                List<Integer> sponsorIds = new ArrayList<>();
                for (Sponsor sponsor : event.getSponsors()) {
                    sponsorIds.add(sponsor.getSponsorId());
                }
                List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
                if (sponsorIds.size() != sponsors.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some sponsors not available");
                }
                existEvent.setSponsors(sponsors);

            }

            return eventJpaRepository.save(existEvent);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteEvent(int eventId) {
        try {
            eventJpaRepository.deleteById(eventId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Sponsor> getEventSponsors(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();

            return event.getSponsors();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}