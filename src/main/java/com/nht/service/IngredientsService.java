package com.nht.service;

import com.nht.Model.IngredientCategory;
import com.nht.Model.IngredientItem;

import java.util.List;

public interface IngredientsService {


    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws  Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception;

    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngredientItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientItem updateStock(Long id) throws Exception;
}
