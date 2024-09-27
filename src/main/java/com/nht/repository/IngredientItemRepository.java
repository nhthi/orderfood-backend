package com.nht.repository;

import com.nht.Model.IngredientItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientItem,Long> {
    List<IngredientItem> findByRestaurantId(Long id);
}
