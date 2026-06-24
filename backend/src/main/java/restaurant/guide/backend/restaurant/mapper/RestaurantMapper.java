package restaurant.guide.backend.restaurant.mapper;

import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.model.Restaurant;

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
