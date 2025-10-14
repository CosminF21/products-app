package com.example.products_app.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        System.out.println(products);
        for(int i=0; i<=20; i++){
            products.add(
                    new Product().builder()
                            .id(1)
                            .title("Product " + i)
                            .description("Description " + i)
                            .price((i+1) * 1.0)
                            .quantity(1L)
                    .build()
            );
        }

        Mockito.when(productService.fetchProducts())
                .thenReturn(products);

        Mockito.when(productService.updateProduct(any(Product.class)))
                .thenReturn(1);
    }

    @Test
    @WithMockUser(username="user", roles={"role_user"})
    void testUserEndpoints() throws Exception {
        mockMvc.perform(get("/products-app/api/fetchProducts"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/products-app/api/updateProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(0))))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/products-app/api/deleteProduct")
                .param("id", "1"))
                .andExpect(status().isForbidden());
    }

}
