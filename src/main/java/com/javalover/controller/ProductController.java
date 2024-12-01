package com.javalover.controller;

import com.javalover.entity.Product;
import com.javalover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
//operator
    @PostMapping("/search")
    public List<Product> getProductsByMultiplePriceValue(@RequestBody List<Double> prices){
        return productService.getProductsByMultiplePriceValue(prices);
    }

    @GetMapping("/min/{minPrice}/max/{maxPrice}")
    public List<Product> getProductsWithinPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice){
        return productService.getProductsWithinPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/high/{price}")
    public List<Product> getProductsWithHigerPrice(@PathVariable double price){
        return productService.getProductsWithHigerPrice(price);
    }

    @GetMapping("/less/{price}")
    public List<Product> getProductsWithLessPrice(@PathVariable double price){
        return productService.getProductsWithLessPrice(price);
    }
    @GetMapping("/like/{name}")
    public List<Product> getProductsWithLike(@PathVariable String name){
        return productService.getProductsWithLike(name);
    }

    //sorting
    @GetMapping("/sort/{fieldName}")
    public List<Product> getProductsWithSorting(@PathVariable String fieldName){
        return productService.getProductsWithSorting(fieldName);
    }

    //pagination
    @GetMapping("/page/{offset}/{limit}")
    public Page<Product> getProductsWithPageResponse(@PathVariable int offset,@PathVariable int limit) {
        return productService.getProductsWithPageResponse(offset, limit);
    }

    @GetMapping("/pageWithSort/{fieldName}/{offset}/{limit}")
    public Page<Product> getProductsWithSortingAndPagination( @PathVariable String fieldName,@PathVariable int offset,@PathVariable int limit) {
        return productService.getProductsWithSortingAndPagination(fieldName, offset, limit);
    }

}
