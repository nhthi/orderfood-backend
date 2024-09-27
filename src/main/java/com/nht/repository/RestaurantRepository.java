package com.nht.repository;

import com.nht.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select r from Restaurant r where  lower(r.name) like lower(concat('%',:query,'%')) or lower(r.cuisineType) like lower(concat('%',:query,'%') ) ")
    public List<Restaurant> findSearchQuery(@Param("query") String query);


    public Restaurant findByOwnerId(Long userId);

}
