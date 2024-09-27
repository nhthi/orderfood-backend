package com.nht.repository;

import com.nht.Model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {


    public List<Food> findByRestaurantId(Long restaurantId);

    @Query("select f from Food f where f.name like %:keyword% or f.foodCategory.name like %:keyword%")
    public List<Food> searchFoods(@Param("keyword") String keyword);
}
