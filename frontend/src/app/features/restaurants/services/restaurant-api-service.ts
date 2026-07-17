import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Restaurant } from '../models/Restaurants';
import { environment } from '../../../../environments/environment';

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
