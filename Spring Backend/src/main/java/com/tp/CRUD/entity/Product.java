package com.tp.CRUD.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp.CRUD.request.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private UUID reference;

    @Lob
    private String description;

    // @Basic(fetch = FetchType.EAGER)
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    //@ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;


    @JsonBackReference
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    Collection<Facture> factures = new ArrayList<>();

    public ProductDto getDto(){

        ProductDto productDto = new ProductDto();

        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setReference(reference);
        productDto.setByteImg(img);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;

    }
}
