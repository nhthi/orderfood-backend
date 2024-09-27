package com.nht.service;

import com.nht.Model.IngredientCategory;
import com.nht.Model.IngredientItem;
import com.nht.Model.Restaurant;
import com.nht.repository.IngredientCategoryRepository;
import com.nht.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService{

    @Autowired
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;



    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
     }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        if(ingredientCategory.isEmpty()){
            throw new Exception("ingredient category not found with id: "+id);
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);

        IngredientItem ingredientItem = new IngredientItem();
        ingredientItem.setRestaurant(restaurant);
        ingredientItem.setName(ingredientName);
        ingredientItem.setCategory(ingredientCategory);

        ingredientCategory.getIngredients().add(ingredientItem);
        return ingredientItemRepository.save(ingredientItem);
    }

    @Override
    public List<IngredientItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem updateStock(Long id) throws Exception {
        Optional<IngredientItem> otp = ingredientItemRepository.findById(id);
        if(otp.isEmpty()){
            throw new Exception("ingredient item not found with id: "+id);
        }
        IngredientItem ingredientItem1 = otp.get();
        ingredientItem1.setInStoke(!ingredientItem1.isInStoke());
        return ingredientItemRepository.save(ingredientItem1);
    }
}
