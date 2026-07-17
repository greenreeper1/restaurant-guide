package restaurant.guide.backend.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.exceptions.RestaurantNotFoundException;
import restaurant.guide.backend.restaurant.mapper.RestaurantMapper;
import restaurant.guide.backend.restaurant.model.Restaurant;
import restaurant.guide.backend.restaurant.repository.RestaurantRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    public RestaurantResponse getById(Long id){
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));

        log.info("Gotten restaurant : \"{}\"", restaurant.getName());

        return mapper.toRestaurantResponse(restaurant);
    }

    public List<RestaurantResponse> getAll(){
        List<Restaurant> restaurants = repository.findAll();

        log.info("Gotten all {} restaurants", restaurants.size());

        return restaurants.stream()
                .map(mapper::toRestaurantResponse)
                .toList();
    }

    public RestaurantResponse registerRestaurant(RestaurantResponse restaurant){
        Restaurant restaurantToSave = new Restaurant(restaurant.id(), restaurant.name(), restaurant.city(), restaurant.category());
        repository.save(restaurantToSave);

        log.info("Registered restaurant \"{}\"", restaurant.name());

        return restaurant;
    }

    public void deleteRestaurant(Long id){
        log.info("Deleted restaurant with id : {}", id);

        repository.deleteById(id);
    }
}
