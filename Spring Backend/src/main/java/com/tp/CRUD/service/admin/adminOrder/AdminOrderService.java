package com.tp.CRUD.service.admin.adminOrder;

import com.tp.CRUD.request.FactureDto;

import java.util.List;

public interface AdminOrderService {

    public List<FactureDto> getAllPlaceOrder();

    public FactureDto changeOrderStatus(Long orderId , String status);
}
