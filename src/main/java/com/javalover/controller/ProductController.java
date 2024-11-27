package com.javalover.controller;

import com.javalover.entity.Product;
import com.javalover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("/{productType}")
    public List<Product> getProductsByType(@PathVariable String productType) {
        return productService.getProductsByType(productType);
    }

    @GetMapping("/price/{price}/productType/{productType}")
    public List<Product> getProductWithPriceAndType(@PathVariable double price,@PathVariable String productType) {
        return productService.getProductWithPriceAndType(price, productType);
    }
    @GetMapping("/search/{price}")
    public List<Product> getProductByPrice( @PathVariable double price) {
        return productService.getProductsByPrice(price);
    }
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id,@RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public long deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);

    }


}
