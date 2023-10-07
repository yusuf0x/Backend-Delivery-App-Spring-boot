package com.delivery.app.exceptions;

import com.delivery.app.payload.response.ErrorObject;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorObject> handleBrandNotFoundException(
            AddressNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePersonNotFoundException(
            PersonNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorObject> handleOrderNotFoundException(
            OrderNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorObject> handleProductNotFoundException(
            ProductNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler({JwtExpirationException.class, ExpiredJwtException.class})
    public ResponseEntity<ErrorObject> handleJwtExpirationException(
            JwtExpirationException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCategoryNotFoundException(
            CategoryNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(OrderDetailNotFoundException.class)
    public ResponseEntity<ErrorObject> handleOrderDetailNotFoundException(
            OrderDetailNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
}
