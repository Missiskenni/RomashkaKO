
import org.example.Main;
import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository repository;

    @Test
    void testGetProduct() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setName("Test Product");
        product.setDescription("Description");
        product.setPrice(100.0);

        when(repository.findById("1")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setName("Test Product");
        product.setDescription("Description");
        product.setPrice(100.0);

        when(repository.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Test Product",
                                    "description": "Description",
                                    "price": 100.0,
                                    "inStock": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testValidationErrors() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "",
                                "description": "Description",
                                "price": -100.0,
                                "inStock": true
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Название товара обязательно"))
                .andExpect(jsonPath("$.price").value("Цена товара не может быть меньше 0"));
    }

    @Test
    void testGeneralException() throws Exception {
        when(repository.findById("1")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected error"));
    }
}