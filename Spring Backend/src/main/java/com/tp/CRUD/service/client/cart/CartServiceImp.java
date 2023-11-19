package com.tp.CRUD.service.client.cart;

import com.tp.CRUD.entity.*;
import com.tp.CRUD.enums.OrderStatus;
import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.repository.*;
import com.tp.CRUD.request.AddProductInCartDto;
import com.tp.CRUD.request.FactureDto;
import com.tp.CRUD.request.PlaceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService{

    @Autowired
    private FactureRepo factureRepo;


    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CouponRepo couponRepo;



    @Override
    public ResponseEntity<?> addProductToOrder(AddProductInCartDto addProductInCartDto){

        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(addProductInCartDto.getClientId(), OrderStatus.Pending);
        System.out.println("activeFacture "+ activeFacture);


            Optional<Product> optionalProduct = productRepo.findById(addProductInCartDto.getProductId());
            Optional<Client> clientOptional = clientRepo.findById(addProductInCartDto.getClientId());

            if (optionalProduct.isPresent() && clientOptional.isPresent()){

                activeFacture.setProduct(optionalProduct.get());
                activeFacture.setPrice(optionalProduct.get().getPrice());
                activeFacture.setQuantity(1L);
                activeFacture.setTotalAmount(activeFacture.getAmount() + activeFacture.getPrice());
                activeFacture.setAmount(activeFacture.getAmount() + activeFacture.getPrice());

                return ResponseEntity.status(HttpStatus.CREATED).body( factureRepo.save(activeFacture));

            }else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User or Product not found ");
            }


    }


    @Override
    public FactureDto deleteFacture(AddProductInCartDto addProductInCartDto){
        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(addProductInCartDto.getClientId(), OrderStatus.Pending);

        Optional<Facture> optionalFacture = factureRepo.findByProductIdAndClientIdAndOrderStatus(
                addProductInCartDto.getProductId(),addProductInCartDto.getClientId(),OrderStatus.Pending
        );

        if (optionalFacture.isPresent()){

            Facture facture = optionalFacture.get();

            facture.setAmount(0L );
            facture.setTotalAmount(0L);
            facture.setPrice(null);
            facture.setQuantity(null);

            facture.setProduct(null);
            if (activeFacture.getCoupon() != null){

                facture.setCoupon(null);
                facture.setDiscount(0L);
            }

            factureRepo.save(facture);
            //factureRepo.save(activeFacture);
            return activeFacture.getOrderDto();
        }
        return  null;
    }

    @Override
    public FactureDto getFactureByClientId(Long clientId){

        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(clientId, OrderStatus.Pending);

        FactureDto factureDto = new FactureDto();

        factureDto.setAmount(activeFacture.getAmount());
        factureDto.setId(activeFacture.getId());
        factureDto.setOrderStatus(activeFacture.getOrderStatus());
        factureDto.setDiscount(activeFacture.getDiscount());
        factureDto.setTotalAmount(activeFacture.getTotalAmount());
        factureDto.setClientName(activeFacture.getClient().getLastName());
        factureDto.setProductName(activeFacture.getProduct().getName());
        factureDto.setClientId(activeFacture.getClient().getId());
        factureDto.setQuantity(activeFacture.getQuantity());
        factureDto.setProductId(activeFacture.getProduct().getId());
        factureDto.setReturnedImg(activeFacture.getProduct().getImg());
        factureDto.setPrice(activeFacture.getPrice());

        if (activeFacture.getCoupon() != null){
            factureDto.setCouponName(activeFacture.getCoupon().getName());
        }

        return factureDto;
    }

    @Override
    public FactureDto ApplyCoupon(Long clientId, String code){
        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(clientId, OrderStatus.Pending);
        Coupon coupon = couponRepo.findByCode(code).orElseThrow(()->new ValidationException("Coupon not Found"));
    if(couponIsExpired(coupon)){
        throw new ValidationException("Coupon has Expired");
    }

    double discountAmount = ((coupon.getDiscount()/100.0) * activeFacture.getTotalAmount());
    double netAmount = activeFacture.getTotalAmount() - discountAmount;

    activeFacture.setAmount((long) netAmount);
    activeFacture.setDiscount((long) discountAmount);
    activeFacture.setCoupon(coupon);

    factureRepo.save(activeFacture);

    return activeFacture.getOrderDto();


    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return  expirationDate != null && currentDate.after(expirationDate);
    }

    @Override
    public FactureDto increaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(addProductInCartDto.getClientId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepo.findById(addProductInCartDto.getProductId());

        Optional<Facture> optionalFacture = factureRepo.findByProductIdAndClientIdAndOrderStatus(
                addProductInCartDto.getProductId(),addProductInCartDto.getClientId(),OrderStatus.Pending
        );

        if (optionalProduct.isPresent() && optionalFacture.isPresent()){

            Facture facture = optionalFacture.get();
            Product product = optionalProduct.get();

            activeFacture.setAmount(activeFacture.getAmount() + product.getPrice() );
            activeFacture.setTotalAmount(activeFacture.getTotalAmount() + product.getPrice());

            facture.setQuantity(facture.getQuantity() + 1);

            if (activeFacture.getCoupon() != null){

                double discountAmount = ((activeFacture.getCoupon().getDiscount()/100.0) * activeFacture.getTotalAmount());
                double netAmount = activeFacture.getTotalAmount() - discountAmount;

                activeFacture.setAmount((long) netAmount);
                activeFacture.setDiscount((long) discountAmount);

            }

            factureRepo.save(facture);
            factureRepo.save(activeFacture);
            return activeFacture.getOrderDto();
        }
        return  null;
    }



    @Override
    public FactureDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(addProductInCartDto.getClientId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepo.findById(addProductInCartDto.getProductId());

        Optional<Facture> optionalFacture = factureRepo.findByProductIdAndClientIdAndOrderStatus(
                addProductInCartDto.getProductId(),addProductInCartDto.getClientId(),OrderStatus.Pending
        );

        if (optionalProduct.isPresent() && optionalFacture.isPresent()){

            Facture facture = optionalFacture.get();
            Product product = optionalProduct.get();

            activeFacture.setAmount(activeFacture.getAmount() - product.getPrice() );
            activeFacture.setTotalAmount(activeFacture.getTotalAmount() - product.getPrice());

            facture.setQuantity(facture.getQuantity() - 1);

            if (activeFacture.getCoupon() != null){

                double discountAmount = ((activeFacture.getCoupon().getDiscount()/100.0) * activeFacture.getTotalAmount());
                double netAmount = activeFacture.getTotalAmount() - discountAmount;

                activeFacture.setAmount((long) netAmount);
                activeFacture.setDiscount((long) discountAmount);

            }

            factureRepo.save(facture);
            factureRepo.save(activeFacture);
            return activeFacture.getOrderDto();
        }
        return  null;
    }


    @Override
    public FactureDto placeOrder(PlaceOrder placeOrder){
        Facture activeFacture = factureRepo.findByClientIdAndOrderStatus(placeOrder.getClientId(), OrderStatus.Pending);
        Optional<Client> optionalClient = clientRepo.findById(placeOrder.getClientId());

        if (optionalClient.isPresent()){
            activeFacture.setOrderDescription(placeOrder.getOrderDescription());
            activeFacture.setAddress(placeOrder.getAddress());
            activeFacture.setDate(new Date());
            activeFacture.setOrderStatus(OrderStatus.Placed);
            activeFacture.setTrackingId(UUID.randomUUID());

            factureRepo.save(activeFacture);

            ///////////////Order ////////////////////
            Facture facture = new Facture();
            facture.setAmount(0L);
            facture.setTotalAmount(0L);
            facture.setDiscount(0L);
            facture.setClient(optionalClient.get());
            facture.setOrderStatus(OrderStatus.Pending);
            factureRepo.save(facture);


            return activeFacture.getOrderDto();
        }
        return null;
    }


    @Override
    public List<FactureDto> getMyPlaceOrders(Long customerId){
        return  factureRepo.findByClientIdAndOrderStatusIn(customerId , List.of(OrderStatus.Placed,OrderStatus.shipped,OrderStatus.Delivered))
                .stream().map(Facture::getOrderDto).collect(Collectors.toList());
    }
}
