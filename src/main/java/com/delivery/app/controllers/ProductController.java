package com.delivery.app.controllers;

import com.delivery.app.models.Product;
import com.delivery.app.payload.request.ProductRequest;
import com.delivery.app.payload.response.ApiResponse;
import com.delivery.app.payload.response.ApiResponseWithData;
import com.delivery.app.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(new ApiResponseWithData<>(true, "ALL Products", productService.findAll()));
    }
    @PostMapping("/add-new")
    public ResponseEntity<?> addNewProduct(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart ProductRequest product) {
        try {
            productService.addNewProduct(product, files);
            return ResponseEntity.ok(new ApiResponse(true, "Product added Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/top")
    public ResponseEntity<?> getProductsTopHome() {
        try {
            List<Product> topProducts = productService.getTopProducts();
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Top 10 Products", topProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<?> getImagesProduct(@PathVariable Long productId) {
        List<String> productImages = productService.getProductImages(productId);return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get Images Product", productImages));
    }

    @GetMapping("/search-by-product-name")
    public ResponseEntity<?> searchProductForName(@RequestParam String name) {
        List<Product> searchResults = productService.searchProductByName(name);
        return ResponseEntity.ok(new ApiResponseWithData<>(true, "Search products", searchResults));
    }

    @GetMapping("/by-admin")
    public ResponseEntity<?> listProductsAdmin() {
        List<Product> adminProducts = productService.listAdminProducts();
        return ResponseEntity.ok(new ApiResponseWithData<>(true, "List Products for Admin", adminProducts));
    }
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<?> listProductsByCatgeory(@PathVariable Long categoryId) {
            List<Product> products = productService.listProductsByCatgeory(categoryId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "List Products By Category", products));
    }

    @PutMapping("/{productId}/update-status")
    public ResponseEntity<?> updateStatusProduct(@PathVariable Long productId) {
        productService.updateProductStatus(productId);
        return ResponseEntity.ok(new ApiResponse(true, "Product updated"));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully"));
    }

}
