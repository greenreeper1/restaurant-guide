import { Component, inject, signal } from '@angular/core';
import { Restaurant } from '../../models/restaurants';
import { RestaurantStore } from '../../store/restaurant-store';
import { RestaurantCard } from '../../components/restaurant-card/restaurant-card';

@Component({
  selector: 'app-restaurant-list-page',
  imports: [RestaurantCard],
  templateUrl: './restaurant-list-page.html',
  styleUrl: './restaurant-list-page.css',
})
export class RestaurantListPage {
  readonly store = inject(RestaurantStore);
  
  constructor() {
    this.store.load();
  }
}
