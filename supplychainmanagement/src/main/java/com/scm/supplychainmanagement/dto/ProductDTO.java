package com.scm.supplychainmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private Integer quantityAvailable;
    private SupplierDTO supplierDto;
}
