package com.tp.CRUD.controller.admin;

import com.tp.CRUD.request.FactureDto;
import com.tp.CRUD.service.admin.adminOrder.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminOrderController {


    @Autowired
    private AdminOrderService adminOrderService;


    @GetMapping("placeOrders")
    public ResponseEntity<List<FactureDto>> getAllPlaceOrders(){
        return ResponseEntity.ok(adminOrderService.getAllPlaceOrder());
    }


    @GetMapping("order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status){

        FactureDto factureDto = adminOrderService.changeOrderStatus(orderId,status);
        if (factureDto ==  null)
            return new ResponseEntity<>("Something went wrong !", HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.OK).body(factureDto);
    }
}
