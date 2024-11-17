import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository();
    }

    @Test
    void testSaveAndFindById() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Description");
        product.setPrice(100.0);
        product.setInStock(true);

        Product savedProduct = repository.save(product);
        assertNotNull(savedProduct.getId());

        Optional<Product> foundProduct = repository.findById(savedProduct.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals("Test Product", foundProduct.get().getName());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setName("Product 1");
        repository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        repository.save(product2);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setName("Test Product");
        Product savedProduct = repository.save(product);

        repository.delete(savedProduct.getId());
        assertTrue(repository.findById(savedProduct.getId()).isEmpty());
    }
}
