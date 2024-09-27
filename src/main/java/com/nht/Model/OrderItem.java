package com.nht.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JsonIgnore
//    private Restaurant restaurant;

    @ManyToOne
    private Food food;

    private int quantity;

    private Long totalPrice;

    @ElementCollection
    private List<String> ingredients = new ArrayList<>();
}
