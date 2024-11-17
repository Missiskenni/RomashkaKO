package org.example.repositories;

import org.example.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository<T> {
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product save(T t);
    void delete(String id);
}
