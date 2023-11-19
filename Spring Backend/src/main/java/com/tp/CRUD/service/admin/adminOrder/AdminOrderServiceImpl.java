package com.tp.CRUD.service.admin.adminOrder;

import com.tp.CRUD.entity.Facture;
import com.tp.CRUD.enums.OrderStatus;
import com.tp.CRUD.repository.FactureRepo;
import com.tp.CRUD.request.FactureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminOrderServiceImpl implements AdminOrderService{

    @Autowired
    private FactureRepo factureRepo;


    @Override
    public List<FactureDto> getAllPlaceOrder(){
        List<Facture> factureList = factureRepo.findAllByOrderStatusIn(List.of(OrderStatus.Placed,OrderStatus.shipped,OrderStatus.Delivered));

        return  factureList.stream().map(Facture::getOrderDto).collect(Collectors.toList());
    }


    @Override
    public FactureDto changeOrderStatus(Long orderId , String status){

        Optional<Facture> optionalOrder = factureRepo.findById(orderId);

        if (optionalOrder.isPresent()){
            Facture facture = optionalOrder.get();

            if (Objects.equals(status, "Shipped")){
                facture.setOrderStatus(OrderStatus.shipped);
            } else if (Objects.equals(status, "Delivered")) {
                facture.setOrderStatus(OrderStatus.Delivered);
            }
            return factureRepo.save(facture).getOrderDto();
        }
        return null;

    }
}
