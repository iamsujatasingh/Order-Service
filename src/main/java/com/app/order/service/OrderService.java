package com.app.order.service;

import com.app.order.dto.OrderDTO;
import com.app.order.entity.OrderModel;
import com.app.order.repo.OrderRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;


    public OrderDTO getOrderByID(Integer id) {

        return convertToDTO(orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found")));
    }

    public OrderDTO createOrder(OrderDTO orderRequest) {
        OrderModel order = orderRepo.save(new OrderModel(orderRequest.getId(),orderRequest.getOrderDate(),orderRequest.getTotalAmount(),orderRequest.getCustomerName(),orderRequest.getProductCodes()));

        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(OrderModel order) {
        return new OrderDTO(order.getId(), order.getOrderDate(), order.getTotalAmount(),order.getCustomerName(),order.getProductCodes());
    }

}
