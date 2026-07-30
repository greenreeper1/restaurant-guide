import { Component, AfterViewInit } from '@angular/core';
import { Restaurant } from '@features/restaurants/models/Restaurants';
import { MapComponent } from '@features/map/components/map/map';

@Component({
  selector: 'app-map-page',
  imports: [MapComponent],
  standalone: true,
  templateUrl: './map-page.html',
  styleUrl: './map-page.css',
})
export class MapPage implements AfterViewInit {
  restaurants: Restaurant[] = [];

  ngAfterViewInit(): void {}
}
