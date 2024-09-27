package com.nht.service;

import com.nht.Model.Event;
import com.nht.Model.Food;
import com.nht.Model.Restaurant;
import com.nht.repository.EventRepository;
import com.nht.request.CreateEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImp implements EventService{

    @Autowired
    private EventRepository eventRepository;


    @Override
    public Event createEvent(CreateEventRequest req, Restaurant restaurant) {

        Event event = new Event();
        event.setRestaurant(restaurant);
        event.setEventName(req.getEventName());
        event.setImage(req.getImage());
        event.setLocation(req.getLocation());
        event.setStartAt(req.getStartAt());
        event.setEndAt(req.getEndAt());

        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) throws Exception {
            Event event = findEventById(eventId);
            eventRepository.delete(event);
    }

    @Override
    public List<Event> getRestaurantEvents(Long restaurantId) {
        List<Event> events = eventRepository.findByRestaurantId(restaurantId);
        return events;
    }

    @Override
    public Event findEventById(Long eventId) throws Exception {
        Optional<Event> otp = eventRepository.findById(eventId);
        if(otp.isEmpty()){
            throw new Exception("Event not found with id: "+eventId);
        }
        return otp.get();
    }

    @Override
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }
}
