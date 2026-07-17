import { Component, input } from '@angular/core';
import { Restaurant } from '../../models/Restaurants';

@Component({
  selector: 'app-restaurant-card',
  imports: [],
  templateUrl: './restaurant-card.html',
  styleUrl: './restaurant-card.css',
})
export class RestaurantCard {
  restaurant = input.required<Restaurant>();
}
