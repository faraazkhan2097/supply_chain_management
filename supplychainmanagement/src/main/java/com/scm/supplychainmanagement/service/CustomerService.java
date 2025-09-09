package com.scm.supplychainmanagement.service;

import com.scm.supplychainmanagement.Respository.CustomerRepository;
import com.scm.supplychainmanagement.dto.CustomerDTO;
import com.scm.supplychainmanagement.entities.Customer;
import com.scm.supplychainmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository _customerRepository;

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = _customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomersById(Long id){
        Customer customer = _customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }

    public Customer createCustomer(CustomerDTO customerDto){
        Customer customer = new Customer();
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setCustomerAddress(customerDto.getCustomerAddress());

        return _customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDto){
        Customer customer = _customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customer.setCustomerName(customerDto.getCustomerName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setCustomerAddress(customerDto.getCustomerAddress());

        return _customerRepository.save(customer);
    }

    public void deleteCustomer(Long id){
        Customer customer = _customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        _customerRepository.delete(customer);
    }

    public CustomerDTO convertToDTO(Customer customer){
        return new CustomerDTO(customer.getId(), customer.getCustomerName(), customer.getEmail(), customer.getPhoneNumber(), customer.getCustomerAddress());
    }
}
