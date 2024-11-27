package com.javalover.service;

import com.javalover.entity.Product;
import com.javalover.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).get();
    }
    public Product getProductByName(String name) {
        return repository.findByName(name);
    }
    public List<Product> getProductsByType(String productType) {
        return repository.findByProductType(productType);
    }

    public List<Product> getProductWithPriceAndType(double price, String productType) {
        return repository.findByPriceAndProductType(price,productType);
    }

    public List<Product> getProductsByPrice(double price) {
        return repository.getProductByPrice(price);
    }

    public Product updateProduct(int id,Product product) {
        Product existingProduct = repository.findById(id).get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductType(product.getProductType());
        existingProduct.setDescription(product.getDescription());
        return repository.save(existingProduct);
    }
    public long deleteProduct(int id) {
        repository.deleteById(id);
        return repository.count();
    }

}
