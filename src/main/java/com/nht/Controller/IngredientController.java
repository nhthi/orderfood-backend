package com.nht.Controller;


import com.nht.Model.IngredientCategory;
import com.nht.Model.IngredientItem;
import com.nht.request.IngredientCategoryRequest;
import com.nht.request.IngredientRequest;
import com.nht.service.IngredientsService;
import com.nht.service.RestaurantService;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;



    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
    ) throws Exception {

        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(),req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<IngredientItem> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientItem item = ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientItem> items = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> items = ingredientsService.findIngredientCategoriesByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
