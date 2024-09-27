package com.nht.service;

import com.nht.Model.Category;
import com.nht.Model.Event;
import com.nht.Model.Food;
import com.nht.Model.Restaurant;
import com.nht.request.CreateEventRequest;
import com.nht.request.CreateFoodRequest;

import java.util.List;

public interface EventService {
    public Event createEvent(CreateEventRequest req, Restaurant restaurant);
    public void deleteEvent(Long eventId)throws Exception;
    public List<Event> getRestaurantEvents(Long restaurantId);
    public Event findEventById(Long eventId) throws Exception;
    public List<Event> findAllEvent();
}
