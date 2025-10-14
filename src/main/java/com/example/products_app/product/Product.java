package com.example.products_app.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    private Integer id;

    @Column(
            unique = true,
            nullable = false
    )
    private String title;


    private String description;

    @Column(
            nullable = false
    )
    private Double price;

    @Column(
            nullable = false
    )
    private Long quantity;

}
