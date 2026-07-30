import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Restaurant } from '@features/restaurants/models/Restaurants';
import { environment } from '@src/environments/environment.dev'; //! A changer selon config

@Injectable({
  providedIn: 'root',
})
export class RestaurantApiService {
  private readonly http = inject(HttpClient);

  private readonly apiUrl = environment.apiUrl;

  getAll() {
    return this.http.get<Restaurant[]>(`${this.apiUrl}/restaurants`);
  }

  getById(id: number) {
    return this.http.get<Restaurant>(`${this.apiUrl}/restaurants/${id}`);
  }
}
