package com.nht.service;

import com.nht.Model.Category;
import com.nht.Model.Food;
import com.nht.Model.Restaurant;
import com.nht.repository.FoodRepository;
import com.nht.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantService restaurantService;





    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setImages(req.getImages());
        food.setVegetarian(req.isVegetarin());
        food.setSeasonal(req.isSeasonal());
        food.setCreationDate(new Date());
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
            Food food = findFoodById(foodId);
//            food.setFoodCategory(null);
        foodRepository.delete(food);
//            food.setRestaurant(null);
//            foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarin,
                                        boolean isNonVeg,
                                        boolean isSeasonal,
                                        String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarin){
            foods = filterByVegetarin(foods,isVegetarin);
        }
        if(isNonVeg){
            foods = filterByNonveg(foods,isNonVeg);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.equals("")){
            foods = filterByCategory(foods,foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());

    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());

    }

    private List<Food> filterByVegetarin(List<Food> foods, boolean isVegetarin) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarin).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFoods(String keyword) {
        return foodRepository.searchFoods(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food =  foodRepository.findById(foodId);
        if(food.isEmpty()){
            throw new Exception("Food not exist with id :"+foodId);
        }
        return food.get();
    }

    @Override
    public Food updateAvailibilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
