package com.app.order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class
OrderModel {
    @Id
    private int id;

    private LocalDate orderDate;
    private double totalAmount;
    private String customerName;

    @ElementCollection
    private List<String> productCodes;

    public OrderModel(int id, LocalDate orderDate, double totalAmount, String customerName, List<String> productCodes) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
        this.productCodes = productCodes;
    }

    public OrderModel() {
    }
}
