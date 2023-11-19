package com.tp.CRUD.controller.admin;

import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.request.ProductDto;
import com.tp.CRUD.service.admin.product.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/admin/")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;


    @PostMapping("product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {

        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @PutMapping("product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@ModelAttribute ProductDto productDto,@PathVariable Long productId) throws IOException {
        try {

            ProductDto updatedProduct = adminProductService.updateProduct(productDto, productId);
            return ResponseEntity.ok(updatedProduct);

        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("products")
    public  ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDto = adminProductService.getAllProduct();
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("search/{name}")
    public  ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String name){
        List<ProductDto> productDto = adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("product/{productId}")
    public  ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
       boolean delete = adminProductService.deleteProduct(productId);
       if (delete) {
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getProductByID/{productId}")
    public  ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){

        try {
            ProductDto productDto = adminProductService.getProductById(productId);
            return ResponseEntity.ok(productDto);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
