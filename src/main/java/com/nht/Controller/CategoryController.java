package com.nht.Controller;

import com.nht.Model.Category;
import com.nht.Model.User;
import com.nht.service.CategoryService;
import com.nht.service.RestaurantService;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;



    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Category createCategory = categoryService.createCategory(category.getName(),user.getId());
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant/{restaurantId}")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @PathVariable("restaurantId") Long restaurantId
    ) throws Exception {
        List<Category> categorires = categoryService.findCategoryByRestaurantId(restaurantId);
        return new ResponseEntity<>(categorires, HttpStatus.OK);
    }

}
