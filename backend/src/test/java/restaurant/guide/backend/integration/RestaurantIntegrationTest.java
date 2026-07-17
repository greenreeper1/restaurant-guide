package restaurant.guide.backend.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import restaurant.guide.backend.restaurant.model.Restaurant;
import restaurant.guide.backend.restaurant.repository.RestaurantRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RestaurantIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository repository;

    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        restaurant1 = new Restaurant();
        restaurant1.setName("Test name 1");
        restaurant1.setCity("Test city 1");
        restaurant1.setCategory("Test category 1");

        restaurant2 = new Restaurant();
        restaurant2.setName("Test name 2");
        restaurant2.setCity("Test city 2");
        restaurant2.setCategory("Test category 2");

        repository.saveAll(List.of(restaurant1, restaurant2));
    }

    @Test
    void shouldReturnAllRestaurants() throws Exception {
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].name").value("Test name 1"))
                .andExpect(jsonPath("$[0].city").value("Test city 1"))
                .andExpect(jsonPath("$[0].category").value("Test category 1"))

                .andExpect(jsonPath("$[1].name").value("Test name 2"))
                .andExpect(jsonPath("$[1].city").value("Test city 2"))
                .andExpect(jsonPath("$[1].category").value("Test category 2"));
    }

    @Test
    void shouldReturnRestaurantById() throws Exception{
        mockMvc.perform(get("/api/restaurants/" + restaurant1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurant1.getId()))
                .andExpect(jsonPath("$.name").value("Test name 1"))
                .andExpect(jsonPath("$.city").value("Test city 1"))
                .andExpect(jsonPath("$.category").value("Test category 1"));
    }

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/restaurants/666"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
