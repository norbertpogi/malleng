package com.order.processing.orderprocessing.controller;

import com.order.processing.orderprocessing.entity.Order;
import com.order.processing.orderprocessing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/process")
    public ResponseEntity<?> processOrders(@RequestBody Map<String, String> request) {
        String filePath = request.get("filePath");
        try {
            orderService.processOrders(filePath);
            return ResponseEntity.ok(Map.of("message", "Batch processing started successfully. 100 orders are being processed."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
