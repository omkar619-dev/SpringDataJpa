package com.javalover.repository;

import com.javalover.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String name);

    List<Product> findByProductType(String productType);

    List<Product> findByPriceAndProductType(double price, String productType);

    @Query(value = "SELECT * FROM product WHERE price= ?1",nativeQuery = true)
    List<Product> getProductByPrice(double price);

}
