package com.scm.supplychainmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    private Long id;
    private Long orderId;
    private String carrier;
    private String trackingNumber;
    private LocalDate shippedDate;
    private LocalDate estimatedDeliveryDate;
    private LocalDate actualDeliveryDate;
    private String status;
}
