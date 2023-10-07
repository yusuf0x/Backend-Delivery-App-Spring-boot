package com.delivery.app.exceptions;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public ProductNotFoundException(String message){
        super(message);
    }
}
