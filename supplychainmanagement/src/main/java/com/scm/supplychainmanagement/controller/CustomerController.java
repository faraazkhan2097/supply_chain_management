package com.scm.supplychainmanagement.controller;

import com.scm.supplychainmanagement.dto.CustomerDTO;
import com.scm.supplychainmanagement.entities.Customer;
import com.scm.supplychainmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scm/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService _customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(_customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(_customerService.getCustomersById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDto){
        Customer createdCustomer = _customerService.createCustomer(customerDto);
        CustomerDTO responseDto = _customerService.convertToDTO(createdCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDto){
        Customer updatedCustomer = _customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(_customerService.convertToDTO(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        _customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
