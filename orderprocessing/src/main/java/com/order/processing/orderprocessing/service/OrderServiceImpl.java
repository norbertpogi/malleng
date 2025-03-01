package com.order.processing.orderprocessing.service;

import com.order.processing.orderprocessing.entity.Order;
import com.order.processing.orderprocessing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional  // Ensures database transactions are committed
    public void processOrders(String filePath) {
        if (!Files.exists(Paths.get(filePath))) {
            throw new RuntimeException("File not found: " + filePath);
        }

        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                System.out.println("Processing Line: " + line); // Debugging

                String[] fields = line.split("\\|");
                if (fields.length != 6) {
                    System.out.println("Skipping Invalid Row: " + line);
                    continue;
                }

                try {
                    Order order = new Order();
                    order.setOrderId(fields[0].trim());
                    order.setCustomerId(fields[1].trim());
                    order.setOrderDate(LocalDate.parse(fields[2].trim()));
                    order.setProductCode(fields[3].trim());
                    order.setQuantity(Integer.parseInt(fields[4].trim()));
                    order.setPricePerUnit(new BigDecimal(fields[5].trim()));
                    order.setTotalPrice(order.getPricePerUnit().multiply(BigDecimal.valueOf(order.getQuantity())));
                    order.setStatus("PROCESSED");
                    order.setErrorMessage(null);

                    orders.add(order);
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Skipping invalid row: " + line);
                }
            }

            // Save to DB and Log the Number of Records Saved
            if (!orders.isEmpty()) {
                orderRepository.saveAll(orders);
                System.out.println("Successfully saved " + orders.size() + " orders to DB.");
            } else {
                System.out.println("No valid orders found to save.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage());
        }
    }


    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
