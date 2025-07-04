package com.app.order.repo;


import com.app.order.entity.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderModel, Integer> {
}
