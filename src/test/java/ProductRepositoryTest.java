import org.example.Main;
import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;


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

        int curSize = repository.findAll().size();

        Product product1 = new Product();
        product1.setName("Product 1");
        repository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        repository.save(product2);

        assertEquals(curSize+2, repository.findAll().size());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setName("Test Product");
        Product savedProduct = repository.save(product);

        repository.deleteById(savedProduct.getId());
        assertTrue(repository.findById(savedProduct.getId()).isEmpty());
    }
}
