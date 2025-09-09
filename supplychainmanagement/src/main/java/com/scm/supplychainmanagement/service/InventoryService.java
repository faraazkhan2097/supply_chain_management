package com.scm.supplychainmanagement.service;

import com.scm.supplychainmanagement.Respository.InventoryRepository;
import com.scm.supplychainmanagement.Respository.ProductRepository;
import com.scm.supplychainmanagement.dto.InventoryDTO;
import com.scm.supplychainmanagement.entities.Inventory;
import com.scm.supplychainmanagement.entities.Product;
import com.scm.supplychainmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository _inventoryRepository;
    private final ProductRepository _productRepository;

    public InventoryDTO getInventoryByProductId(Long productId){
        Inventory inventory = _inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    Product product = _productRepository.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    newInventory.setQuantityAvailable(0);
                    return _inventoryRepository.save(newInventory);
                });
        return convertToDTO(inventory);
    }

    @Transactional
    public InventoryDTO updateInventory(Long productId, Integer quantity){
        Inventory inventory = _inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    Product product = _productRepository.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    newInventory.setQuantityAvailable(0);
                    return newInventory;
                });

        inventory.setQuantityAvailable(quantity);
        Inventory saved = _inventoryRepository.save(inventory);
        return convertToDTO(saved);
    }

    @Transactional
    public void reduceInventory(Long productId, Integer quantity){
        Inventory inventory = _inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + productId));
        int currentQty = inventory.getQuantityAvailable();
        int updatedQty = currentQty - quantity;
        if(updatedQty < 0){
            throw new IllegalArgumentException("Insufficient inventory for product id: " + productId);
        }
        inventory.setQuantityAvailable(updatedQty);
        _inventoryRepository.save(inventory);
    }

    private InventoryDTO convertToDTO(Inventory inventory){
        return new InventoryDTO(
                inventory.getId(),
                inventory.getProduct().getId(),
                inventory.getProduct().getProductName(),
                inventory.getQuantityAvailable()
        );
    }
}
