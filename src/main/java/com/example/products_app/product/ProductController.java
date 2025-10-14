package com.example.products_app.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products-app/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/fetchProducts")
    public List<Product> fetchProducts(){
        return productService.fetchProducts();
    }

    @PostMapping("/insertProduct")
    public String insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    @PutMapping("/updateProduct")
    public Integer updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/deleteProduct")
    public String deleteProduct(@RequestParam int id){
        productService.deleteProduct(id);
        return "Product with id " + id + " deleted";
    }
}
