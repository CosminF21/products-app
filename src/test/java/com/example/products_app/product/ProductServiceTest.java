package com.example.products_app.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(ProductService.class)
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Test
    void testFetchProducts_empty() {
        List<Product> products = productService.fetchProducts();
        Assertions.assertThat(products.isEmpty());
    }

    @Test
    void insertProduct() {
        Product product = Product.builder()
                .title("title")
                .description("desc")
                .price(500.0)
                .quantity(10L)
                .build();

        String title = productService.insertProduct(product);
        Assertions.assertThat("title".equals(title));
        Assertions.assertThat(productRepo.findAll().size()).isEqualTo(1);
    }

    @Test
    void updateProduct() {
        Product product = Product.builder()
                .title("title")
                .description("desc")
                .price(500.0)
                .quantity(10L)
                .build();

        productService.insertProduct(product);
        product.setTitle("Updated");
        Integer id = productService.updateProduct(product);

        Product updated = productRepo.findById(id).orElseThrow();
        Assertions.assertThat(updated.getTitle().equals("Updated"));
    }

    @Test
    void deleteProduct() {
        Product p = productRepo.save(
                Product.builder()
                .title("title")
                .description("desc")
                .price(500.0)
                .quantity(10L)
                .build()
        );

        productService.deleteProduct(p.getId());
        Assertions.assertThat(productRepo.findById(p.getId()).isEmpty());
    }
}
