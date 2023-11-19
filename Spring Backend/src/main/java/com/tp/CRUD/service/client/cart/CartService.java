package com.tp.CRUD.service.client.cart;

import com.tp.CRUD.request.AddProductInCartDto;
import com.tp.CRUD.request.FactureDto;
import com.tp.CRUD.request.PlaceOrder;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    public ResponseEntity<?> addProductToOrder(AddProductInCartDto addProductInCartDto);

    public FactureDto deleteFacture(AddProductInCartDto addProductInCartDto);

    public FactureDto getFactureByClientId(Long customerId);


    public FactureDto ApplyCoupon(Long customerId, String code);

    public FactureDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

    public FactureDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

    public FactureDto placeOrder(PlaceOrder placeOrder);

    public List<FactureDto> getMyPlaceOrders(Long customerId);
}
