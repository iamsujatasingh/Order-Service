package com.app.order.dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int id;
    private LocalDate orderDate;
    private double totalAmount;
    private String customerName;
    @ElementCollection
    private List<String> productCodes;
}
