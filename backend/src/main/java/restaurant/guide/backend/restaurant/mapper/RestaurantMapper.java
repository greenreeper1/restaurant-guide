package restaurant.guide.backend.restaurant.mapper;

import org.springframework.stereotype.Component;

import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.model.Restaurant;

@Component
public class RestaurantMapper {
    
    public RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
        return new RestaurantResponse(
            restaurant.getId(),
            restaurant.getName(),
            restaurant.getCity(),
            restaurant.getCategory()
        );
    }
}
