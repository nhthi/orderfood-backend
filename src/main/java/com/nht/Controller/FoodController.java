package com.nht.Controller;

import com.nht.Model.Food;
import com.nht.Model.User;
import com.nht.service.FoodService;
import com.nht.service.RestaurantService;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;




    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam("name") String name,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFoods(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantfood(
            @RequestParam("vegetarian") boolean vegetarian,
            @RequestParam("seasonal") boolean seasonal,
            @RequestParam("nonveg") boolean nonveg,
            @RequestParam(value = "food_category",required = false) String food_category,
            @PathVariable("restaurantId") Long restaurantId
    ) throws Exception {
        List<Food> foods = foodService.getRestaurantFood(restaurantId,vegetarian,nonveg,seasonal,food_category);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
