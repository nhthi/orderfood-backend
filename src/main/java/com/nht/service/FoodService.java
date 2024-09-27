package com.nht.service;

import com.nht.Model.Category;
import com.nht.Model.Food;
import com.nht.Model.Restaurant;
import com.nht.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
    public void deleteFood(Long foodId)throws Exception;
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarin,
                                        boolean isNonVeg,
                                        boolean isSeasonal,
                                        String foodCategory
    );
    public List<Food> searchFoods(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailibilityStatus(Long foodId) throws Exception;
}
