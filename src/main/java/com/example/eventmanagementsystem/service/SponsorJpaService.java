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
public class SponsorJpaService implements SponsorRepository {
    @Autowired
    private EventJpaRepository eventJpaRepository;
    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Sponsor> getSponsors() {
        return (ArrayList<Sponsor>) sponsorJpaRepository.findAll();
    }

    @Override
    public Sponsor getSponsorById(int sponsorId) {
        try {
            return sponsorJpaRepository.findById(sponsorId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        try {
            List<Integer> eventIds = new ArrayList<>();
            for (Event event : sponsor.getEvents()) {
                eventIds.add(event.getEventId());
            }
            List<Event> events = eventJpaRepository.findAllById(eventIds);
            sponsor.setEvents(events);
            for (Event event : events) {
                event.getSponsors().add(sponsor);
            }
            eventJpaRepository.saveAll(events);

            return sponsorJpaRepository.save(sponsor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        try {
            Sponsor existSponsor = sponsorJpaRepository.findById(sponsorId).get();
            if (sponsor.getSponsorName() != null) {
                existSponsor.setSponsorName(sponsor.getSponsorName());
            }
            if (sponsor.getIndustry() != null) {
                existSponsor.setIndustry(sponsor.getIndustry());
            }
            if (sponsor.getEvents() != null) {
                List<Event> events = existSponsor.getEvents();
                for (Event event : events) {
                    event.getSponsors().remove(existSponsor);
                }
                eventJpaRepository.saveAll(events);
                List<Integer> newEventIds = new ArrayList<>();
                for (Event event : sponsor.getEvents()) {
                    newEventIds.add(event.getEventId());
                }
                List<Event> newEvents = eventJpaRepository.findAllById(newEventIds);
                for (Event event : newEvents) {
                    event.getSponsors().add(existSponsor);
                }
                eventJpaRepository.saveAll(newEvents);
                existSponsor.setEvents(newEvents);

            }
            return sponsorJpaRepository.save(existSponsor);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            List<Event> events = sponsor.getEvents();
            for (Event event : events) {
                event.getSponsors().remove(sponsor);
            }
            eventJpaRepository.saveAll(events);
            sponsorJpaRepository.deleteById(sponsorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Event> getSponsorEvents(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();

            return sponsor.getEvents();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}