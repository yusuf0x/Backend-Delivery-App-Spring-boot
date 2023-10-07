package com.delivery.app;

import com.delivery.app.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BookTest {
    @Autowired
    private ProductService productService;
    public BookTest() {}

    @Test
    public void itShouldCreateASuccessfully(){}

}
