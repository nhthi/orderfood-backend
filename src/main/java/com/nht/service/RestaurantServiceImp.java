package com.nht.service;

import com.nht.Model.Address;
import com.nht.Model.Restaurant;
import com.nht.Model.User;
import com.nht.dto.RestaurantDto;
import com.nht.repository.AddressRepository;
import com.nht.repository.RestaurantRepository;
import com.nht.repository.UserRepository;
import com.nht.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;




    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setDescription(req.getDescription());
        restaurant.setName(req.getName());
        restaurant.setImages(req.getImages());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long retaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(retaurantId);
        if(updateRestaurant.getAddress() != null) {
            restaurant.setAddress(updateRestaurant.getAddress());
        }
        if(updateRestaurant.getContactInformation() != null) {
            restaurant.setContactInformation(updateRestaurant.getContactInformation());
        }
        if(updateRestaurant.getDescription() != null) {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if(updateRestaurant.getName() != null) {
            restaurant.setName(updateRestaurant.getName());
        }
        if(updateRestaurant.getImages() != null) {
            restaurant.setImages(updateRestaurant.getImages());
        }
        if(updateRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(updateRestaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(updateRestaurant.getOpeningHours());
        }

        return null;
    }

    @Override
    public void deleteRestaurant(Long retaurantId) throws Exception {
            Restaurant restaurant = findRestaurantById(retaurantId);
            restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll() ;
    }

    @Override
    public List<Restaurant> searchRestaurant(String searchRestaurant) {
        return restaurantRepository.findSearchQuery(searchRestaurant);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if(opt.isEmpty()) {
            throw new Exception("Restaurant not found with id: "+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null) {
            throw new Exception("Restaurant not found with owner id: "+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurantId);
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setOpen(restaurant.isOpen());

        boolean isFavourtited = false;
        List<Restaurant> favourites = user.getFavourites();

        for(Restaurant favourite : favourites){
            if(favourite.getId().equals(restaurantId)){
                isFavourtited = true;
                break;
            }
        }
        if(isFavourtited){
            favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
        }else{
            favourites.add(restaurant);
        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllFavouritesUser(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return user.getFavourites();
    }
}
