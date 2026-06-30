package restaurant.guide.backend.restaurant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import restaurant.guide.backend.restaurant.model.Restaurant;

@DataJpaTest
public class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository repository;

    private Restaurant restaurant;

    Restaurant createRestaurant(String name, String city, String category){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setCity(city);
        restaurant.setCategory(category);
        return restaurant;
    }

    @BeforeEach
    void setUp(){
        restaurant = createRestaurant("Test name", "Test city", "Test category");
    }

    @Test
    void shouldFindRestaurantById() {
        Restaurant saved = repository.save(restaurant);

        assertTrue(repository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldFindRestaurantsByCity() {
        Restaurant restaurant2 = createRestaurant("Test name 2", "Test city", "Test category");
        Restaurant restaurant3 = createRestaurant("Test name 3", "Test city 2", "Test category");

        repository.saveAll(List.of(restaurant, restaurant2, restaurant3));

        List<Restaurant> result = repository.findByCity("Test city");

        assertEquals(2, result.size());

        assertTrue(result.stream().allMatch(r -> r.getCity().equals("Test city")));
    }

    @Test
    void shouldReturnAllRestaurants() {
        Restaurant restaurant2 = createRestaurant("Test city 2", "Test city", "Test category");
        
        repository.saveAll(List.of(restaurant, restaurant2));

        List<Restaurant> restaurants = repository.findAll();

        assertEquals(2, restaurants.size());
    }
}
