package com.scm.supplychainmanagement.service;


import com.scm.supplychainmanagement.Respository.SupplierRepository;
import com.scm.supplychainmanagement.entities.Supplier;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository _supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository){
        this._supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers(){
        return _supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id){
        return _supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id "+ id));
    }

    public Supplier createSupplier(Supplier supplier){
        return _supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier updatedSupplier){
        Supplier existingSupplier = getSupplierById(id);
        existingSupplier.setName(updatedSupplier.getName());
        existingSupplier.setAddress(updatedSupplier.getAddress());
        existingSupplier.setContactPerson(updatedSupplier.getContactPerson());
        existingSupplier.setPhoneNumber(updatedSupplier.getPhoneNumber());
        return _supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long id){
        Supplier existingSupplier = getSupplierById(id);
        _supplierRepository.delete(existingSupplier);
    }
}
