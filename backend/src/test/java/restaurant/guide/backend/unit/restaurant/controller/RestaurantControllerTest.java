package restaurant.guide.backend.unit.restaurant.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import restaurant.guide.backend.restaurant.controller.RestaurantController;
import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.model.RestaurantCategory;
import restaurant.guide.backend.restaurant.service.RestaurantService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RestaurantService restaurantService;

    @Test
    void shouldReturnRestaurantById() throws Exception {
        RestaurantResponse restaurant = new RestaurantResponse(
            1L,
            "Test name",
            "Test city",
            RestaurantCategory.AFRICAINE
        );

        when(restaurantService.getById(1L)).thenReturn(restaurant);

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test name"))
                .andExpect(jsonPath("$.city").value("Test city"))
                .andExpect(jsonPath("$.category").value("AFRICAINE"));
        
        verify(restaurantService).getById(1L);
    }

    @Test
    void shouldReturnAllRestaurants() throws Exception {
        RestaurantResponse restaurant1 = new RestaurantResponse(
            1L,
            "Test name 1",
            "Test city 1",
            RestaurantCategory.AFRICAINE
        );

        RestaurantResponse restaurant2 = new RestaurantResponse(
            2L,
            "Test name 2",
            "Test city 2",
            RestaurantCategory.ASIATIQUE
        );

        when(restaurantService.getAll()).thenReturn(List.of(restaurant1, restaurant2));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test name 1"))
                .andExpect(jsonPath("$[1].name").value("Test name 2"));
        
        verify(restaurantService).getAll();
    }

    @Test
    void shouldRegisterRestaurant() throws Exception {
        RestaurantResponse restaurant = new RestaurantResponse(
            1L,
            "Test name 1",
            "Test city 1",
            RestaurantCategory.AFRICAINE
        );

        when(restaurantService.registerRestaurant(any(RestaurantResponse.class)))
                .thenReturn(restaurant);

        // When / Then
        mockMvc.perform(post("/api/restaurants/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test name 1"))
                .andExpect(jsonPath("$.city").value("Test city 1"))
                .andExpect(jsonPath("$.category").value("AFRICAINE"));

        verify(restaurantService, times(1))
                .registerRestaurant(any(RestaurantResponse.class));
    }
}
