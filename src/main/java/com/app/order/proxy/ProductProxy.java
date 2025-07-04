package com.app.order.proxy;

import com.app.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name="Product-app",url ="http://localhost:8010/api/product",configuration =FeignClientConfig.class)
public interface ProductProxy {

    @GetMapping("/get/{id}")
    ResponseEntity<ProductDTO> getProductByID(@PathVariable("id") Integer id);

    @GetMapping("/test")
    ResponseEntity<String> testHello();

    @PostMapping("/validate-list")
    ResponseEntity<Map<String, Boolean>> validateProductCodes(@RequestBody List<String> productCodes) ;

}
