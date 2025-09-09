package com.scm.supplychainmanagement.service;

import com.scm.supplychainmanagement.Respository.ProductRepository;
import com.scm.supplychainmanagement.Respository.SupplierRepository;
import com.scm.supplychainmanagement.dto.ProductDTO;
import com.scm.supplychainmanagement.dto.SupplierDTO;
import com.scm.supplychainmanagement.entities.Product;
import com.scm.supplychainmanagement.entities.Supplier;
import com.scm.supplychainmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository _productRepository;
    private final SupplierRepository _supplierRepository;

    public List<ProductDTO> getAllProducts(){
        List<Product> products = _productRepository.findAll();
        return products.stream().map(product -> {
            SupplierDTO supplierDto = null;

            if(product.getSupplier() != null){
                supplierDto = new SupplierDTO(
                        product.getSupplier().getId(),
                        product.getSupplier().getName()
                );
            }
            return new ProductDTO(
                    product.getId(),
                    product.getProductName(),
                    product.getDescription(),
                    product.getUnitPrice(),
                    product.getQuantityAvailable(),
                    supplierDto
            );
        }).collect(Collectors.toList());
    }

    public List<Product> getProductBySupplierId(Long supplierId){
        return _productRepository.findBySupplierId(supplierId);
    }

    public ProductDTO getProductsById(Long id){
        Product product = _productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+ id));

        Supplier s = product.getSupplier();
        SupplierDTO supplierDto = s != null ? new SupplierDTO(s.getId(), s.getName()) : null;

        return new ProductDTO(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getQuantityAvailable(),
                supplierDto
                );
    }

    @Transactional
    public Product createProduct(ProductDTO productDto){
        if(productDto.getSupplierDto() == null || productDto.getSupplierDto().getId() == null){
            throw new IllegalArgumentException(("Supplier must be provided with the valid id"));
        }
        Long supplierId = productDto.getSupplierDto().getId();
        Supplier supplier = _supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: "+ supplierId));
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setQuantityAvailable(productDto.getQuantityAvailable());
        product.setSupplier(supplier);

        return _productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO productDto){
        Product product = _productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+ id));
        Supplier supplier = _supplierRepository.findById(productDto.getSupplierDto().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: "+ productDto.getSupplierDto().getId()));

        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setQuantityAvailable(productDto.getQuantityAvailable());
        product.setSupplier(supplier);
        return _productRepository.save(product);
    }

    public void deleteProduct(Long id){
        Product product = _productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+ id));
        _productRepository.delete(product);
    }
}