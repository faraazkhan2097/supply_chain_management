package com.scm.supplychainmanagement.service;

import com.scm.supplychainmanagement.dto.ShipmentDTO;
import com.scm.supplychainmanagement.entities.Customer;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void notifyShipmentCreated(Customer customer, ShipmentDTO shipmentDto){
        String message = "Dear" + customer.getCustomerName() + ", your order #" + shipmentDto.getOrderId()
                + " has been shipped via " + shipmentDto.getCarrier()
                + ". Estimated delivery date is " + shipmentDto.getEstimatedDeliveryDate() + ".";

        System.out.println("Sending notification: " + message);
    }
}
