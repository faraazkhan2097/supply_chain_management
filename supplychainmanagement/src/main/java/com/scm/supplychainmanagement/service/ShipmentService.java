package com.scm.supplychainmanagement.service;

import com.scm.supplychainmanagement.Respository.OrderRepository;
import com.scm.supplychainmanagement.Respository.ShipmentRepository;
import com.scm.supplychainmanagement.dto.ShipmentDTO;
import com.scm.supplychainmanagement.entities.Order;
import com.scm.supplychainmanagement.entities.Shipment;
import com.scm.supplychainmanagement.entities.ShipmentStatus;
import com.scm.supplychainmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository _shipmentRepository;
    private final OrderRepository _orderRepository;

    public ShipmentDTO createShipment(Long orderId, String carrier, String trackingNumber, LocalDate estimatedDeliveryDate){
        Order order = _orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setCarrier(carrier);
        shipment.setTrackingNumber(trackingNumber);
        shipment.setShippedDate(LocalDate.now());
        shipment.setEstimatedDeliveryDate(estimatedDeliveryDate);
        shipment.setStatus(ShipmentStatus.PENDING);

        return convertToDTO(_shipmentRepository.save(shipment));
    }

    public ShipmentDTO getShipmentByOrderId(Long orderId){
        Shipment shipment = _shipmentRepository.findOrderById(orderId);
        if (shipment == null) throw new ResourceNotFoundException("Shipment not found for order id: " + orderId);
        return convertToDTO(shipment);
    }

    @Transactional
    public ShipmentDTO updateStatus(Long shipmentId, String status){
        Shipment shipment = _shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + shipmentId));
        shipment.setStatus(ShipmentStatus.valueOf(status));
        return convertToDTO(_shipmentRepository.save(shipment));
    }

    @Transactional
    public ShipmentDTO confirmDelivery(Long shipmentId, LocalDate deliveryDate){
        Shipment shipment = _shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));
        shipment.setActualDeliveryDate(deliveryDate);
        shipment.setStatus(ShipmentStatus.DELIVERED);
        return convertToDTO(_shipmentRepository.save(shipment));
    }

    private ShipmentDTO convertToDTO(Shipment shipment){
        return new ShipmentDTO(
                shipment.getId(),
                shipment.getOrder().getId(),
                shipment.getCarrier(),
                shipment.getTrackingNumber(),
                shipment.getShippedDate(),
                shipment.getEstimatedDeliveryDate(),
                shipment.getActualDeliveryDate(),
                shipment.getStatus().name()
        );
    }

}
