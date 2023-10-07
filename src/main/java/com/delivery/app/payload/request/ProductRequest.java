package com.delivery.app.payload.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private Double price;
    private Long categoryId;
}
