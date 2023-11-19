package com.tp.CRUD.controller.custumer;

import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.request.AddProductInCartDto;
import com.tp.CRUD.request.FactureDto;
import com.tp.CRUD.request.PlaceOrder;
import com.tp.CRUD.service.client.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/")
@CrossOrigin
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;



    @PostMapping("cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto){
        return  cartService.addProductToOrder(addProductInCartDto);
    }



    @GetMapping("cart/{clientId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long clientId ){
        FactureDto factureDto = cartService.getFactureByClientId(clientId);
        return  ResponseEntity.status(HttpStatus.OK).body(factureDto);
    }

    @GetMapping("coupon/{clientId}/{code}")
    public ResponseEntity<?> applyCoupon( @PathVariable Long clientId, @PathVariable String code){
        try {
            FactureDto factureDto = cartService.ApplyCoupon(clientId,code);
            return  ResponseEntity.ok(factureDto);
        }catch (ValidationException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }


    @PostMapping("addition")
    public ResponseEntity<FactureDto>  increaseProductQuantity(@RequestBody  AddProductInCartDto addProductInCartDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCartDto));

    }

    @PostMapping("deduction")
    public ResponseEntity<FactureDto>  decreaseProductQuantity(@RequestBody  AddProductInCartDto addProductInCartDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProductInCartDto));

    }

    @PutMapping("cart/delete")
    public ResponseEntity<FactureDto>  deleteProductToOrder( @RequestBody  AddProductInCartDto addProductInCartDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.deleteFacture(addProductInCartDto));

    }

    @PostMapping("placeOrder")
    public ResponseEntity<FactureDto>  decreaseProductQuantity(@RequestBody PlaceOrder placeOrder){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrder));
    }


    @GetMapping("MyOrders/{clientId}")
    public ResponseEntity<List<FactureDto>> getMyPlaceOrders(@PathVariable Long clientId){

        return ResponseEntity.ok(cartService.getMyPlaceOrders(clientId));
    }

}
