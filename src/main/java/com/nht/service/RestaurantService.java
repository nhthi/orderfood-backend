package com.nht.service;

import com.nht.Model.Restaurant;
import com.nht.Model.User;
import com.nht.dto.RestaurantDto;
import com.nht.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(
            CreateRestaurantRequest req, User user);
    public Restaurant updateRestaurant(Long retaurantId, CreateRestaurantRequest updateRestaurant) throws  Exception;

    public void deleteRestaurant(Long retaurantId) throws Exception;

    public List<Restaurant> getAllRestaurants() ;

    public List<Restaurant> searchRestaurant(String searchRestaurant);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;

    public List<Restaurant> getAllFavouritesUser(String jwt) throws Exception;
}
