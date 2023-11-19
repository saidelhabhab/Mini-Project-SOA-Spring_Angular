package com.tp.CRUD.repository;

import com.tp.CRUD.entity.Facture;
import com.tp.CRUD.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepo extends JpaRepository<Facture,Long> {

    Facture findByClientIdAndOrderStatus(Long client_id, OrderStatus orderStatus);


    List<Facture> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);

    List<Facture> findByClientIdAndOrderStatusIn(Long customer_id, List<OrderStatus> orderStatus);

    Optional<Facture> findByProductIdAndClientIdAndOrderStatus(Long productId, Long client_id,OrderStatus orderStatus);
}
