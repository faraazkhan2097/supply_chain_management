package com.scm.supplychainmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDate orderDate;
    private List<OrderItemDTO> orderItemsDto;
    private ShipmentDTO shipmentDto;
}
