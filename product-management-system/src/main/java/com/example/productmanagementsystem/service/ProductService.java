package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.dto.ProductDTO;
import com.example.productmanagementsystem.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getProduct(Long id);
    Product addProduct(ProductDTO productDto);
    Product updateProduct(Long id, ProductDTO productDto);
    Product deleteProduct(Long id);
}
