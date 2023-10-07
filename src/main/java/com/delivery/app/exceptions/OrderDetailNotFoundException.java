package com.delivery.app.exceptions;

import java.io.Serial;

public class OrderDetailNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public OrderDetailNotFoundException(String message){
        super(message);
    }
}
