package com.delivery.app.services;

import com.delivery.app.exceptions.ProductNotFoundException;
import com.delivery.app.models.Category;
import com.delivery.app.models.Product;
import com.delivery.app.payload.request.ProductRequest;
import com.delivery.app.repositories.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final FileService fileService;
    private final UserService userService;
    private final  CategoryService categoryService;

    public ProductService(ProductRepo productRepo, FileService fileService, UserService userService, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.fileService = fileService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    public void addNewProduct(ProductRequest productRequest, MultipartFile[] files) throws IOException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        product.setCategory(category);
        Product savedProduct = productRepo.save(product);
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileService.uploadImage("products/"+savedProduct.getId(),file);
            images.add(fileName);
        }
        savedProduct.setImages(images);
        productRepo.save(savedProduct);
    }
    public List<Product> getTopProducts() {
        return productRepo.findAll();
    }
    public List<Product> findAll() {
        return productRepo.findAll();
    }
    public Product getProductById(Long productId){
        return productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product Not FOund with id="+productId)
        );
    }
    public List<String> getProductImages(Long productId){
        Product p = getProductById(productId);
        return p.getImages();
    }
    public List<Product> searchProductByName(String productName) {
        return productRepo.findByNameContaining(productName);
    }
    public List<Product> listAdminProducts() {
        return productRepo.findAll();
    }
    public List<Product> listProductsByCatgeory(Long catgeoryId){
        Category category = categoryService.getCategoryById(catgeoryId);
        return productRepo.findByCategory(category);
    }
    public void updateProductStatus(Long productId) {
        Product product = getProductById(productId);
        product.setStatus(!product.isStatus());
        productRepo.save(product);
    }
    public void deleteProduct(Long productId) {
        Product p = getProductById(productId);
        productRepo.delete(p);
    }
}
