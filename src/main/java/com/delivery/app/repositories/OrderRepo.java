package com.delivery.app.repositories;

import com.delivery.app.models.Order;
import com.delivery.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByClient(User user);
    List<Order> findByStatus(String status);
    List<Order> findByDeliveryAndStatus(User user,String status);
}
