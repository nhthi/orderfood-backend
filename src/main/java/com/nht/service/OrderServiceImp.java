package com.nht.service;

import com.nht.Model.*;
import com.nht.repository.*;
import com.nht.request.OrderRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {
        Address shipAddress = req.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shipAddress);

         if(!user.getAddresses().contains(savedAddress)){
             user.getAddresses().add(savedAddress);
             userRepository.save(user);
         }

        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());

        Order createOrder = new Order();
        createOrder.setCustomer(user);
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(shipAddress);
        createOrder.setRestaurant(restaurant);
        createOrder.setCreateAt(new Date());

//        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        Long totalPrice = 0L;

        for(CartItem cartItem : req.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());

//            orderItem.setRestaurant(cartItem.getFood().getRestaurant());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            totalPrice += cartItem.getTotalPrice();
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);
        return savedOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String updateStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(
                updateStatus.equals("OUT_FOR_DELIVERY") ||
                updateStatus.equals("DELIVERED") ||
                updateStatus.equals("COMPLETED")||
                updateStatus.equals("PENDING")
        ){
            order.setOrderStatus(updateStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null && !orderStatus.equals("ALL")){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception{
        Optional<Order> otp = orderRepository.findById(orderId);
        if(otp.isEmpty()){
            throw new Exception("Order not found with id " + orderId);
        }
        return otp.get();
    }

}
