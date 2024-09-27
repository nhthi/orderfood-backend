package com.nht.repository;

import com.nht.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findCartByCustomerId(Long userId);

}
