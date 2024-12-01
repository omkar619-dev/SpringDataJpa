package com.javalover.service;

import com.javalover.entity.Product;
import com.javalover.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

//Operator
    public List<Product> getProductsByMultiplePriceValue(List<Double> prices){
        return repository.findByPriceIn(prices);
    }

    public List<Product> getProductsWithinPriceRange(double minPrice, double maxPrice){
        return repository.findByPriceBetween(minPrice,maxPrice);
    }

    public List<Product> getProductsWithHigerPrice(double price){
        return repository.findByPriceGreaterThan(price);
    }


    public List<Product> getProductsWithLessPrice(double price){
        return repository.findByPriceLessThan(price);
    }

    public List<Product> getProductsWithLike(String name){
        return repository.findByNameIgnoreCaseContaining(name);
    }
    //sorting
    public List<Product> getProductsWithSorting(String fieldName){
        return repository.findAll(Sort.by(Sort.Direction.ASC, fieldName));
    }

    //pagination
    public Page<Product> getProductsWithPageResponse(int offset, int limit) {
        return repository.findAll(PageRequest.of(offset,limit));
    }

    public Page<Product> getProductsWithSortingAndPagination(String fieldName, int offset, int limit) {
        return repository.findAll(PageRequest.of(offset,limit)
                .withSort(Sort.by(fieldName)));
    }

}
