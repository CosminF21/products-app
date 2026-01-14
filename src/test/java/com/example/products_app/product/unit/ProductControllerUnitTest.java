package com.example.products_app.product.unit;

import com.example.products_app.product.Product;
import com.example.products_app.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
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
    void whenUserCall_FetchInsert_ReturnStatusOk() throws Exception {
        mockMvc.perform(get("/products-app/api/fetchProducts")
                        .with(getRole("role_user")))
                .andExpect(status().isOk());

        mockMvc.perform(put("/products-app/api/updateProduct")
                        .with(getRole("role_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(0))))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenUserCall_UpdateDelete_ReturnStatusForbidden() throws Exception {
        mockMvc.perform(put("/products-app/api/updateProduct")
                        .with(getRole("role_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(1))))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/products-app/api/deleteProduct")
                        .with(getRole("role_user"))
                        .param("id", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenAdminCallAnythingReturnStatusOk() throws Exception {
        mockMvc.perform(get("/products-app/api/fetchProducts")
                        .with(getRole("role_admin")))
                .andExpect(status().isOk());

        mockMvc.perform(post("/products-app/api/insertProduct")
                        .with(getRole("role_admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(products.get(0))))
                .andExpect(status().isOk());

        mockMvc.perform(put("/products-app/api/updateProduct")
                        .with(getRole("role_admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products.get(1))))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/products-app/api/deleteProduct")
                        .with(getRole("role_admin"))
                        .param("id", "1"))
                .andExpect(status().isOk());
    }

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor getRole(String role){
        return jwt().authorities(
                createAuthorityList("ROLE_" + role)
        );
    }

}
