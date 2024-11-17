package org.example.controllers;

import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    // Получить список всех товаров
    @GetMapping
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // Получить товар по ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать новый товар
    @PostMapping
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        return ResponseEntity.ok(repository.save(product));
    }

    // Обновить существующий товар
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @Validated @RequestBody Product product) {
        if (!repository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        return ResponseEntity.ok(repository.save(product));
    }

    // Удалить товар по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }

}
