package com.nht.repository;

import com.nht.Model.Category;
import com.nht.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findByRestaurantId(Long restaurantId);
}
