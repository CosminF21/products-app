package com.example.products_app.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepo productRepo;

    public List<Product> fetchProducts() {
        logger.info("Fetching all products...");
        return productRepo.findAll();
    }

    @Transactional
    public String insertProduct(Product product) {
        logger.info("Inserting product {}", product.toString());
        return productRepo.save(product).getTitle();
    }

    @Transactional
    public Integer updateProduct(Product product) {
        logger.info("Updating product {}", product.toString());
        checkExistence(product.getId());
        return productRepo.save(product).getId();
    }

    @Transactional
    public void deleteProduct(int id) {
        logger.info("Deleting product {}", id);
        checkExistence(id);
        productRepo.deleteById(id);
    }

    private void checkExistence(int id){
        productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
    }
}
