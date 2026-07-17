import { inject, Injectable, signal } from '@angular/core';
import { RestaurantApiService } from '../services/restaurant-api-service';
import { Restaurant } from '../models/Restaurants';

@Injectable({
  providedIn: 'root',
})
export class RestaurantStore {
  private readonly api = inject(RestaurantApiService);

  readonly restaurants = signal<Restaurant[]>([]);

  readonly loading = signal(false);

  load(): void {
    this.loading.set(true);

    this.api.getAll().subscribe({
      next: (restaurants) => {
        this.restaurants.set(restaurants);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      },
    });
  }
}
