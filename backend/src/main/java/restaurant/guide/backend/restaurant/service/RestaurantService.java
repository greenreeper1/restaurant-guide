package restaurant.guide.backend.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.exceptions.RestaurantNotFoundException;
import restaurant.guide.backend.restaurant.mapper.RestaurantMapper;
import restaurant.guide.backend.restaurant.model.Restaurant;
import restaurant.guide.backend.restaurant.repository.RestaurantRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    public RestaurantResponse getById(Long id){
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));

        return mapper.toRestaurantResponse(restaurant);
    }

    public List<RestaurantResponse> getAll(){
        List<Restaurant> restaurants = repository.findAll();

        return restaurants.stream()
                .map(mapper::toRestaurantResponse)
                .toList();
    }

    public RestaurantResponse registerRestaurant(RestaurantResponse restaurant){
        Restaurant restaurantToSave = new Restaurant(restaurant.id(), restaurant.name(), restaurant.city(), restaurant.category());
        repository.save(restaurantToSave);
        return restaurant;
    }

    public RestaurantResponse deleteRestaurant(RestaurantResponse restaurantResponse){
        repository.deleteById(restaurantResponse.id());
        return restaurantResponse;
    }
}
