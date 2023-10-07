package com.delivery.app.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class NewOrderRequest {
    private Long clientId;
    private Long addressId;
    private Double total;
    private String typePayment;
    private List<Long> productIds;
}
