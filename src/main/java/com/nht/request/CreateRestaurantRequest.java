package com.nht.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nht.Model.Address;
import com.nht.Model.ContactImformation;
import com.nht.Model.Food;
import com.nht.Model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactImformation contactInformation;
    private String openingHours;
    private List<String> images ;
}
