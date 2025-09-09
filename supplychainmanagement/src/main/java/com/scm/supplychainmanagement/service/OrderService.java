package com.scm.supplychainmanagement.service;

import com.scm.supplychainmanagement.Respository.CustomerRepository;
import com.scm.supplychainmanagement.Respository.OrderRepository;
import com.scm.supplychainmanagement.Respository.ProductRepository;
import com.scm.supplychainmanagement.dto.OrderDTO;
import com.scm.supplychainmanagement.dto.OrderItemDTO;
import com.scm.supplychainmanagement.entities.Customer;
import com.scm.supplychainmanagement.entities.Order;
import com.scm.supplychainmanagement.entities.OrderItem;
import com.scm.supplychainmanagement.entities.Product;
import com.scm.supplychainmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository _orderRepository;
    private final CustomerRepository _customerRepository;
    private final ProductRepository _productRepository;

    public List<OrderDTO> getAllOrders(){
        return _orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id){
        Order order = _orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: "+ id));
        return convertToDTO(order);
    }
    @Transactional
    public Order createOrder(OrderDTO orderDto){
        Customer customer = _customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderDto.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(orderDto.getOrderDate() != null ? orderDto.getOrderDate() : LocalDate.now());

        List<OrderItem> orderItems = orderDto.getOrderItemsDto().stream()
                .map(itemDto -> {
                    Product product = _productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantityOrdered(itemDto.getQuantityOrdered());
                    orderItem.setUnitPrice(product.getUnitPrice());
                    BigDecimal totalPrice = product.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantityOrdered()));
                    orderItem.setTotalPrice(totalPrice);
                    return orderItem;
                }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        return _orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDto){
        Order order = _orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        Customer customer = _customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderDto.getCustomerId()));

        order.setCustomer(customer);
        order.setOrderDate(orderDto.getOrderDate());

//        clear existing items and add updated
        order.getOrderItems().clear();

        List<OrderItem> updatedItems = orderDto.getOrderItemsDto().stream()
                .map(orderItemDto -> {
                    Product product = _productRepository.findById(orderItemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product  not found with id: " + orderItemDto.getProductId()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantityOrdered(orderItemDto.getQuantityOrdered());
                    orderItem.setUnitPrice(product.getUnitPrice());
                    BigDecimal totalPrice = product.getUnitPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantityOrdered()));
                    orderItem.setTotalPrice(totalPrice);
                    return orderItem;
                }).collect(Collectors.toList());

        order.getOrderItems().addAll(updatedItems);
        return _orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        Order order = _orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        _orderRepository.delete(order);
    }

    public OrderDTO convertToDTO(Order order){
        List<OrderItemDTO> orderItemDtos = order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDto = new OrderItemDTO();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getId());
            itemDto.setProductName(item.getProduct().getProductName());
            itemDto.setQuantityOrdered(item.getQuantityOrdered());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setTotalPrice(item.getTotalPrice());
            return  itemDto;
        }).collect(Collectors.toList());

        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(order.getId());
        orderDto.setCustomerId(order.getCustomer().getId());
        orderDto.setCustomerName((order.getCustomer().getCustomerName()));
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setOrderItemsDto(orderItemDtos);
        return orderDto;
    }
}
