package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.dto.ProductDTO;
import com.example.productmanagementsystem.entity.Product;
import com.example.productmanagementsystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;
    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product addProduct(ProductDTO productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStatus(productDto.getStatus());
        repository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDto) {
        Product product = repository.findById(id).orElse(null);
        if(Objects.nonNull(product)){
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setStatus(productDto.getStatus());
            repository.save(product);
        }
        return product;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = repository.findById(id).orElse(null);
        if(Objects.nonNull(product)){
            repository.deleteById(id);
        }
        return product;
    }
}
