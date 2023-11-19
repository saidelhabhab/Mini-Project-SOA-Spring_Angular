package com.tp.CRUD.service.admin.product;

import com.tp.CRUD.entity.Category;
import com.tp.CRUD.entity.Product;
import com.tp.CRUD.exception.ResourceNotFoundException;
import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.repository.CategoryRepo;
import com.tp.CRUD.repository.ProductRepo;
import com.tp.CRUD.request.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;


    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {

        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setReference(UUID.randomUUID());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);

        return productRepo.save(product).getDto();
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductByName(String name) {

        List<Product> products = productRepo.findAllNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto updatedProductDto, Long productId) throws IOException {
        Product existingProduct = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setReference(UUID.randomUUID());

        if (updatedProductDto.getImg() != null) {
            existingProduct.setImg(updatedProductDto.getImg().getBytes());
        }


        Category category = categoryRepo.findById(updatedProductDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingProduct.setCategory(category);

        // Save
        return productRepo.save(existingProduct).getDto();
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()){
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ProductDto getProductById(Long id) throws IOException {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Product not found with id: " + id));

        // Convert Product entity to ProductDto
        return product.getDto();
    }


}
