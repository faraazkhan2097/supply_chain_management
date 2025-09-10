package com.scm.supplychainmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "shipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    private String carrier;
    private String trackingNumber;
    private LocalDate shippedDate;
    private LocalDate estimatedDeliveryDate;
    private LocalDate actualDeliveryDate;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}
