package com.tp.CRUD.request;

import com.tp.CRUD.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FactureDto {
    private Long id;

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    private Long quantity;

    private Long productId;
    private Long clientId;

    private String clientName;
    private Long price;

    private String productName;
    private byte[] returnedImg;

    private String couponName;

}
