import { AfterViewInit, Component, inject } from '@angular/core';
import L from 'leaflet';
import { MapService } from '@features/map/services/map-api-service';

@Component({
  selector: 'app-map',
  imports: [],
  templateUrl: './map.html',
  styleUrl: './map.css',
})
export class MapComponent implements AfterViewInit {
  private map: L.Map | null = null;

  private readonly api: MapService = inject(MapService);

  ngAfterViewInit(): void {
    this.map = this.api.initMap(this.map);
  }
}
