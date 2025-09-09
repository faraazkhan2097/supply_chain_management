package com.scm.supplychainmanagement.controller;

import com.scm.supplychainmanagement.dto.InventoryDTO;
import com.scm.supplychainmanagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scm/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService _inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable Long productId){
        return ResponseEntity.ok(_inventoryService.getInventoryByProductId(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long productId, @RequestParam Integer quantity){
        return ResponseEntity.ok(_inventoryService.updateInventory(productId, quantity));
    }
}
