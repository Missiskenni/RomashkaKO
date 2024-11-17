import org.example.Main;
import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        Product product1 = new Product("1", "Apple", "Fresh apple", 10.0, true);
        Product product2 = new Product("2", "Orange", "Sweet orange", 15.0, true);
        Product product3 = new Product("3", "Banana", "Yellow banana", 5.0, false);

        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @Test
    void testFilterByName() {
        List<Product> products = productService.getFilteredAndSortedProducts("Apple", null, null, null, null, null, 10);
        assertEquals(1, products.size());
        assertEquals("Apple", products.get(0).getName());
    }

    @Test
    void testFilterByPriceRange() {
        List<Product> products = productService.getFilteredAndSortedProducts(null, 5.0, 12.0, null, null, null, 10);
        assertEquals(2, products.size());
    }

    @Test
    void testFilterByInStock() {
        List<Product> products = productService.getFilteredAndSortedProducts(null, null, null, true, null, null, 10);
        assertEquals(2, products.size());
    }
}
