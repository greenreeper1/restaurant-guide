import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/restaurants/pages/restaurant-list-page/restaurant-list-page').then(
        (m) => m.RestaurantListPage,
      ),
  },
  /*{
        path: 'restaurants/:id',
        loadComponent: () => 
            import('./features/restaurants/pages/restaurant-detail-pages')
                .then(m => m.RestaurantDetailPage)
    }*/
  {
    path: 'map',
    loadComponent: () => import('./features/map/components/map/map').then((m) => m.MapComponent),
    //   import('./features/restaurants/pages/map-page/map-page').then((m) => m.MapPage),
  },
];
