package restaurant.guide.backend.restaurant.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    private final long restaurantId;

    public RestaurantNotFoundException(Long restaurantId){
        super("User with id " + restaurantId + " was not found");
        this.restaurantId = restaurantId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }
    
}
