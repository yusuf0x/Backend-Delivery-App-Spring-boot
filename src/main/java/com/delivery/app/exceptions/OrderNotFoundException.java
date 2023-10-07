package com.delivery.app.exceptions;

import java.io.Serial;

public class OrderNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public OrderNotFoundException(String message){
        super(message);
    }
}
