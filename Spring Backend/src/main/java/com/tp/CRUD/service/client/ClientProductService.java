package com.tp.CRUD.service.client;


import com.tp.CRUD.request.ProductDto;

import java.util.List;


public interface ClientProductService {

    public List<ProductDto> getAllProduct();


    public List<ProductDto> searchProductByTitle(String name);

}
