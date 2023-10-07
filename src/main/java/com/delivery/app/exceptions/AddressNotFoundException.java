package com.delivery.app.exceptions;

import java.io.Serial;

public class AddressNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public AddressNotFoundException(String message){
        super(message);
    }
}
