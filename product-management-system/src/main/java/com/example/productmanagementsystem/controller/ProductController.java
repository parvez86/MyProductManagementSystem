package com.example.productmanagementsystem.controller;


import com.example.productmanagementsystem.dto.ProductDTO;
import com.example.productmanagementsystem.entity.Product;
import com.example.productmanagementsystem.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductServiceImpl service;

    @GetMapping
    List<Product> getProducts(){
        System.out.println("get all is called");
        return service.getAll();
    }

    @GetMapping("/{id}")
    Product getProduct(@PathVariable("id") Long id)
    {
        System.out.println("get method is called");
        return service.getProduct(id);
    }
    @PostMapping
    Product addProduct(@Valid @RequestBody ProductDTO productDto){
        System.out.println(productDto);
        return service.addProduct(productDto);
    }

    @PutMapping("/{id}")
    Product updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDto){
        System.out.println("update api is called");
        return service.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    Product deleteProduct(@PathVariable("id") Long id){
        System.out.println("delete api is called");
        return service.deleteProduct(id);
    }
}
