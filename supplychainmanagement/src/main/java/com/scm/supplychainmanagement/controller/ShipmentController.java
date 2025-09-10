package com.scm.supplychainmanagement.controller;

import com.scm.supplychainmanagement.dto.ShipmentDTO;
import com.scm.supplychainmanagement.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/scm/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService _shipmentService;

    @PostMapping
    public ResponseEntity<ShipmentDTO> createShipment(@RequestBody ShipmentDTO shipmentDto){
        ShipmentDTO created = _shipmentService.createShipment(
                shipmentDto.getOrderId(),
                shipmentDto.getCarrier(),
                shipmentDto.getTrackingNumber(),
                shipmentDto.getEstimatedDeliveryDate()
        );
        return ResponseEntity.ok(created);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShipmentDTO> getShipmentByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(_shipmentService.getShipmentByOrderId(orderId));
    }

    @PutMapping("/{shipmentId}/deliver")
    public ResponseEntity<ShipmentDTO> confirmDelivery(@PathVariable Long shipmentId, @RequestBody Map<String, String> body){
        LocalDate deliveryDate = LocalDate.parse(body.get("actualDeliveryDate"));
        return ResponseEntity.ok(_shipmentService.confirmDelivery(shipmentId, deliveryDate));
    }

    @PutMapping("/{shipmentId}/status")
    public ResponseEntity<ShipmentDTO> updateShipmentStatus(@PathVariable Long shipmentId, @RequestBody Map<String, String> body){
        String status = body.get("status");
        return ResponseEntity.ok(_shipmentService.updateStatus(shipmentId, status));

    }
}
