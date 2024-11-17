package org.example.service;

import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getFilteredAndSortedProducts(String name, Double minPrice, Double maxPrice, Boolean inStock, String sortBy, String sortDirection, int limit) {

        // Валидация параметров сортировки
        if (sortBy != null && !List.of("name", "price").contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy value. Must be 'name' or 'price'.");
        }

        if (sortDirection != null && !List.of("ASC", "DESC").contains(sortDirection.toUpperCase())) {
            throw new IllegalArgumentException("Invalid sortDirection value. Must be 'ASC' or 'DESC'.");
        }

        // Создаём спецификацию для фильтрации
        Specification<Product> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }

        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (inStock != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("inStock"), inStock));
        }

        // Устанавливаем сортировку
        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection == null ? "ASC" : sortDirection.toUpperCase()), sortBy);
        }

        // Ограничиваем количество записей
        Pageable pageable = PageRequest.of(0, limit, sort);

        // Возвращаем данные
        return productRepository.findAll(spec, pageable).getContent();
    }
}