package com.scm.supplychainmanagement.controller;

import com.scm.supplychainmanagement.dto.ProductDTO;
import com.scm.supplychainmanagement.entities.Product;
import com.scm.supplychainmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scm/products")
public class ProductController {
    private final ProductService _productService;

    @Autowired
    public ProductController(ProductService productService) {
        this._productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = _productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<Product> getProductBySupplierId(@PathVariable Long supplierId){
        return _productService.getProductBySupplierId(supplierId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        ProductDTO productDto = _productService.getProductsById(id);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDto){
        ProductDTO createdProduct = _productService.getProductsById(_productService.createProduct(productDto).getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
     public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDto){
        ProductDTO updatedProduct = _productService.getProductsById(_productService.updateProduct(id, productDto).getId());
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        _productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}

