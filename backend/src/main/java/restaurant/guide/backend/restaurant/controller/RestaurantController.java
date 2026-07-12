package restaurant.guide.backend.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import restaurant.guide.backend.restaurant.dto.RestaurantResponse;
import restaurant.guide.backend.restaurant.service.RestaurantService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id) {
        return service.getById(id);
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
