package com.nht.Controller;


import com.nht.Model.Address;
import com.nht.Model.CartItem;
import com.nht.Model.Order;
import com.nht.Model.User;
import com.nht.repository.AddressRepository;
import com.nht.repository.UserRepository;
import com.nht.request.AddCartItemRequest;
import com.nht.request.OrderRequest;
import com.nht.respone.PaymentResponse;
import com.nht.service.OrderService;
import com.nht.service.PaymentService;
import com.nht.service.RestaurantService;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {


    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(
            @RequestBody OrderRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req,user);
        PaymentResponse res = paymentService.createPaymentLink(order);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUserOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/address")
    public ResponseEntity<Address> createAddress(@RequestHeader("Authorization")String jwt,@RequestBody  Address reqAddress) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Address address = new Address();
        address.setStreetAddress(reqAddress.getStreetAddress());
        address.setCity(reqAddress.getCity());
        address.setPostalCode(reqAddress.getPostalCode());
        address.setCountry("Việt Nam");
        address.setStateProvince(reqAddress.getStateProvince());
        Address newAddress = addressRepository.save(address);
        user.getAddresses().add(address);
        userRepository.save(user);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }
}
