package restaurant.guide.backend.restaurant.dto;

import restaurant.guide.backend.restaurant.model.RestaurantCategory;

public record RestaurantResponse(Long id, String name, String city, RestaurantCategory category) {}
