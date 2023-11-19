package com.tp.CRUD.entity;


import com.tp.CRUD.enums.OrderStatus;
import com.tp.CRUD.request.FactureDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "factures")
@Data
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long price;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "coupon_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private  Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name ="product_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;



    public FactureDto getOrderDto(){
        FactureDto factureDto = new FactureDto();

        factureDto.setId(id);
        factureDto.setOrderDescription(orderDescription);
        factureDto.setAddress(address);
        factureDto.setTrackingId(trackingId);
        factureDto.setQuantity(quantity);
        factureDto.setAmount(amount);
        factureDto.setPrice(price);
        factureDto.setDate(date);
        factureDto.setOrderStatus(orderStatus);
        factureDto.setReturnedImg(product.getImg());
        factureDto.setClientId(client.getId());
        factureDto.setClientName(client.getLastName());

        factureDto.setProductId(product.getId());
        factureDto.setProductName(product.getName());
        factureDto.setReturnedImg(product.getImg());

        if (coupon != null){
            factureDto.setCouponName(coupon.getName());
        }

        return factureDto;
    }
}
