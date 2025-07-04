package com.app.order.controller;

import com.app.order.dto.OrderDTO;
import com.app.order.dto.ProductDTO;
import com.app.order.entity.OrderModel;
import com.app.order.proxy.ProductProxy;
import com.app.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductProxy productProxy;


    @GetMapping("/get")
    public ResponseEntity<OrderDTO> getOrderByID(@RequestParam Integer id){
        OrderDTO order = orderService.getOrderByID(id);
        return ResponseEntity.ok().body(order);
    }
    @PostMapping("/create")
    @CircuitBreaker(name="create-order",fallbackMethod = "fallbackGetProductById")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderRequest) {
        try {
            ResponseEntity<Map<String, Boolean>> response = productProxy.validateProductCodes(orderRequest.getProductCodes());

            Map<String, Boolean> validationResult = response.getBody();

            List<String> invalidCodes = validationResult.entrySet().stream()
                    .filter(entry -> !entry.getValue())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (!invalidCodes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid product codes: " + invalidCodes);
            }
            OrderDTO savedOrder = orderService.createOrder(orderRequest);
            return new ResponseEntity(savedOrder,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Unable to validate product at the moment.");
        }
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<String> check(@PathVariable("id") int id){
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);

    }

    @GetMapping("/product/get/{id}")
    @CircuitBreaker(name="product-service", fallbackMethod ="fallbackGetProductById")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") int id){
        return productProxy.getProductByID(id);

    }

    public ResponseEntity<ProductDTO> fallbackGetProductById(int id, Throwable throwable) {
        ProductDTO fallbackProduct = new ProductDTO();
        fallbackProduct.setId(id);
        fallbackProduct.setName("Fallback Product");
        fallbackProduct.setDescription("This is a fallback product because the Product Service is unavailable.");

        return new ResponseEntity<>(fallbackProduct, HttpStatus.SERVICE_UNAVAILABLE);
    }


}
