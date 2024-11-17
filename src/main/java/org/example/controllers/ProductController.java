package org.example.controllers;

import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.example.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductService productService;

    public ProductController(ProductRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
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
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //Сортировка и фильтрация
    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> getFilteredAndSortedProducts(
            @RequestParam(required = false) String name,               // Фильтр по названию
            @RequestParam(required = false) Double minPrice,           // Фильтр по минимальной цене
            @RequestParam(required = false) Double maxPrice,           // Фильтр по максимальной цене
            @RequestParam(required = false) Boolean inStock,           // Фильтр по наличию
            @RequestParam(required = false) String sortBy,             // Поле для сортировки
            @RequestParam(required = false) String sortDirection,      // Направление сортировки: ASC или DESC
            @RequestParam(required = false, defaultValue = "10") int limit // Ограничение количества записей
    ) {
        List<Product> products = productService.getFilteredAndSortedProducts(name, minPrice, maxPrice, inStock, sortBy, sortDirection, limit);
        return ResponseEntity.ok(products);
    }

}
