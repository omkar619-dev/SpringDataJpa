package com.javalover;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalover.controller.ProductController;
import com.javalover.entity.Product;
import com.javalover.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringDataJpaApplicationTests {
//    @Test
//    public void  getSensitiveInfoEncryptor() {
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword("javalover");
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("base64");
//        encryptor.setConfig(config);
//        System.out.println(encryptor.encrypt("root"));
//    }
    @Autowired
    private ProductController productController;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductRepository productRepository;
    @Before("")
    public void setup(){
    this.mockMvc = MockMvcBuilders.standaloneSetup(ProductController.class).build();
    }
    @Test
    public void addProductTest() throws Exception {
        Product demoProduct = new Product(1,"Laptop", 50000.0, "A high-performance laptop", "Electronics");
        when(productRepository.save(any())).thenReturn(demoProduct);
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .content(convertObjectAsString(demoProduct))
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
    @Test
    public void getProductsTest() throws Exception {
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(1,"Laptop", 50000.0, "A high-performance laptop", "Electronics")
                ,new Product(2,"Laptop2", 40000.0, "A decent-performance laptop", "Electronics")));
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1));
    }
    @Test
    public void getProductByIdTest() throws Exception {
        when(productRepository.findById(108)).thenReturn(Optional.of(new Product(108,"Laptop2",3000,"A decent-performance laptop","Electronics")));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/id/" + 108)
                        .accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(108));
    }
    @Test
    public void updateProductTest() throws Exception {
        Product demoProduct = new Product(10,"Laptop2",3000,"A decent-performance laptop","Electronics");
        when(productRepository.findById(10)).thenReturn(Optional.of(demoProduct));
        when(productRepository.save(any())).thenReturn(new Product(10,"LaptopX",5000,"An insane laptop","Electronics"));
        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 10)
                .content(convertObjectAsString(demoProduct))
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("LaptopX"));

    }
    @Test
    public void deleteProductByIdTest() throws Exception {
        Mockito.doNothing().when(productRepository).deleteById(anyInt());
        when(productRepository.count()).thenReturn(Long.valueOf(100));

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}",12))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").value("100"));
    }



    private String convertObjectAsString(Object object) throws JsonProcessingException {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
