package com.scm.supplychainmanagement.controller;

import com.scm.supplychainmanagement.entities.Supplier;
import com.scm.supplychainmanagement.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scm/suppliers")
public class SupplierController {
    private final SupplierService _supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this._supplierService = supplierService;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return _supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable Long id) {
        return _supplierService.getSupplierById(id);
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier createdSupplier = _supplierService.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        return _supplierService.updateSupplier(id, supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id){
        _supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
