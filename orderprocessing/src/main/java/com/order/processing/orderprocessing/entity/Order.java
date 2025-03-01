package com.order.processing.orderprocessing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private String productCode;
    private String productName;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    private String status;
    private String errorMessage;
}
