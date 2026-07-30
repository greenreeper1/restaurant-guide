import { Injectable } from '@angular/core';
import * as L from 'leaflet';
import { Restaurant } from '@features/restaurants/models/Restaurants';
import { environment } from '@src/environments/environment.dev'; //! A changer selon config

@Injectable({ providedIn: 'root' })
export class MapService {
  private map!: L.Map;

  initMap(map: L.Map | null): L.Map {
    if (!map) {
      map = L.map('map', {
        center: [45.757844266325584, 4.832148262951917],
        zoom: 14,
      });
    }

    const tiles = L.tileLayer(
      'https://tile.jawg.io/jawg-streets/{z}/{x}/{y}{r}.png?access-token={accessToken}',
      {
        attribution:
          '<a href="https://jawg.io" title="Tiles Courtesy of Jawg Maps" target="_blank">&copy; <b>Jawg</b>Maps</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        minZoom: 4,
        maxZoom: 22,
        accessToken: environment.mapToken,
      },
    );

    tiles.addTo(map);
    return map;
  }

  showRestaurants(restaurants: Restaurant[]): L.Marker[] {
    return restaurants.map((restaurant) => this.addMarker(restaurant));
  }

  addMarker(restaurant: Restaurant): L.Marker {
    const marker = L.marker({ lat: restaurant.latitude, lng: restaurant.longitude }).addTo(
      this.map,
    );

    if (restaurant.name) {
      marker.bindPopup(`
        <b>${restaurant.name}</b><br>
        <br><small>${restaurant.latitude}, ${restaurant.longitude}</small>
      `);
    }

    return marker;
  }

  centerOn(restaurant: Restaurant, zoom = 15): void {
    this.map.setView({ lat: restaurant.latitude, lng: restaurant.longitude }, zoom);
  }
}
