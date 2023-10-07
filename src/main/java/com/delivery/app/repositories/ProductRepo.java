package com.delivery.app.repositories;

import com.delivery.app.models.Category;
import com.delivery.app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(Category  category);
}
