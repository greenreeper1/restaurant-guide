package restaurant.guide.backend.unit.restaurant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.exceptions.RestaurantNotFoundException;
import restaurant.guide.backend.restaurant.mapper.RestaurantMapper;
import restaurant.guide.backend.restaurant.model.Restaurant;
import restaurant.guide.backend.restaurant.model.RestaurantCategory;
import restaurant.guide.backend.restaurant.repository.RestaurantRepository;
import restaurant.guide.backend.restaurant.service.RestaurantService;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {
    @Mock
    private RestaurantRepository repository;

    @Mock
    private RestaurantMapper mapper;

    @InjectMocks
    private RestaurantService service;

    private Restaurant restaurant;
    private RestaurantResponse response;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test name");
        restaurant.setCity("Test city");
        restaurant.setCategory(RestaurantCategory.AFRICAINE);

        response = new RestaurantResponse(
            1l,
            "Test name",
            "Test city",
            RestaurantCategory.AFRICAINE
        );
    }

    @Test
    void shouldReturnRestaurantWhenIsExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mapper.toRestaurantResponse(restaurant)).thenReturn(response);

        RestaurantResponse result = service.getById(1L);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).toRestaurantResponse(restaurant);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> service.getById(1L));

        verify(repository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldReturnAllRestaurants() {
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("Test name 2");
        restaurant2.setCity("Test city 2");
        restaurant2.setCategory(RestaurantCategory.ASIATIQUE);

        RestaurantResponse response2 = new RestaurantResponse(
            2L,
            "Test name 2",
            "Test city 2",
            RestaurantCategory.ASIATIQUE
        );

        when(repository.findAll()).thenReturn(List.of(restaurant, restaurant2));

        when(mapper.toRestaurantResponse(restaurant)).thenReturn(response);
        when(mapper.toRestaurantResponse(restaurant2)).thenReturn(response2);

        List<RestaurantResponse> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals(response, result.get(0));
        assertEquals(response2, result.get(1));

        verify(repository).findAll();
        verify(mapper, times(2)).toRestaurantResponse(any(Restaurant.class));
    }
}
