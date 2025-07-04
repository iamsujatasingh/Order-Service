package com.app.order.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private RestTemplate restTemplate=new RestTemplate();

    public String getToken()
    {
        String url = "http://localhost:8010/api/auth/login?userName=admin&password=admin123";

        // Create JSON body
        Map<String, String> request = new HashMap<>();
        request.put("username", "admin");
        request.put("password", "admin123");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        // Send POST request
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("token");  // Adjust key if different
        }

        throw new RuntimeException("Failed to retrieve token");
    }

}