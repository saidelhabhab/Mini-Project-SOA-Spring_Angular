package com.tp.CRUD.repository;

import com.tp.CRUD.entity.Client;
import com.tp.CRUD.entity.Facture;
import com.tp.CRUD.enums.OrderStatus;
import com.tp.CRUD.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client,Long> {


    Optional<Client> findByEmail(String email);

    Client findByRole(UserRole userRole);

    List<Client> findClientsByRole(UserRole userRole);


}
