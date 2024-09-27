package com.nht.request;


import com.nht.Model.Category;
import com.nht.Model.IngredientItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarin;
    private boolean seasonal;
    private List<IngredientItem> ingredients;
}
