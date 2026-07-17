package restaurant.guide.backend.unit.common.advice;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import restaurant.guide.backend.common.advice.GlobalExceptionHandler;
import restaurant.guide.backend.restaurant.controller.RestaurantController;
import restaurant.guide.backend.restaurant.exceptions.RestaurantNotFoundException;
import restaurant.guide.backend.restaurant.service.RestaurantService;

@WebMvcTest(RestaurantController.class)
@Import(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private RestaurantService restaurantService;

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        when(restaurantService.getById(666L)).thenThrow(new RestaurantNotFoundException(666L));

        mockMvc.perform(get("/api/restaurants/666"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
