package com.nht.Controller;

import com.nht.Model.Event;
import com.nht.Model.Restaurant;
import com.nht.Model.User;
import com.nht.request.CreateEventRequest;
import com.nht.respone.MessageResponse;
import com.nht.service.EventService;
import com.nht.service.RestaurantService;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/events")
    public ResponseEntity<Event> createEvent(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateEventRequest req
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Event event = eventService.createEvent(req,restaurant);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @GetMapping("/admin/events/restaurants/{id}")
    public ResponseEntity<List<Event>> getRestaurantEvents(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
//        Restaurant restaurant = restaurantService.findRestaurantById(user.getId());
        List<Event> events = eventService.getRestaurantEvents(id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/admin/events/{id}")
    public ResponseEntity<Event> getEventsById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Event event = eventService.findEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Event> events = eventService.findAllEvent();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @DeleteMapping("/admin/events/{id}")
    public ResponseEntity<MessageResponse> deleteEventById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        eventService.deleteEvent(id);
        MessageResponse msg = new MessageResponse();
        msg.setMessage("Delete Events success, id: "+id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
