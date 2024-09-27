package com.nht.request;

import com.nht.Model.Address;
import com.nht.Model.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
    private List<CartItem> cartItems;
}
