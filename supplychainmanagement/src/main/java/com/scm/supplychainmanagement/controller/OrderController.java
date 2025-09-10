package com.scm.supplychainmanagement.controller;

import com.scm.supplychainmanagement.dto.OrderDTO;
import com.scm.supplychainmanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scm/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService _orderService;

    @PostMapping("/finalize")
    public ResponseEntity<OrderDTO> finalizeOrder(@RequestBody OrderDTO orderDto){
        OrderDTO finalizeOrder = _orderService.finalizeOrder(orderDto);
        return ResponseEntity.ok(finalizeOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        return ResponseEntity.ok(_orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(_orderService.getOrderById(id));
    }

    @PostMapping
    public  ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDto){
        OrderDTO createdOrder = _orderService.convertToDTO(_orderService.createOrder(orderDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDto){
        OrderDTO updatedOrder = _orderService.convertToDTO(_orderService.updateOrder(id, orderDto));
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        _orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
