package restaurant.guide.backend.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.service.RestaurantService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
    
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id){
        service.deleteRestaurant(id);
    }

    @GetMapping("")
    public List<RestaurantResponse> getAll(){
        return service.getAll();
    }
    
    @PostMapping("/register")
    public RestaurantResponse register(RestaurantResponse restaurantResponse){
        return service.registerRestaurant(restaurantResponse);
    }

}
