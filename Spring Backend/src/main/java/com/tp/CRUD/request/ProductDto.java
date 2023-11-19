package com.tp.CRUD.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ProductDto {

    private  Long id;

    private String name;

    private Long  price;

    private UUID reference;

    private String description;

    private byte[] byteImg;

    private Long categoryId;

    private String categoryName;

    private MultipartFile img;
}
