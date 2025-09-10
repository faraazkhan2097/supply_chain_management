package com.scm.supplychainmanagement.Respository;


import com.scm.supplychainmanagement.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>{
    Shipment findOrderById(Long orderId);
}
