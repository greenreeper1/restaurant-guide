import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Restaurant } from '../../restaurants/models/restaurants';

@Injectable({
  providedIn: 'root',
})
export class RestaurantApiService {
  private readonly http = inject(HttpClient);

  getAll() {
    return this.http.get<Restaurant[]>(
      '/api/restaurants'
    );
  }

  getById(id:number){
    return this.http.get<Restaurant>(
      `/api/restaurants/${id}`
    )
  }
}
