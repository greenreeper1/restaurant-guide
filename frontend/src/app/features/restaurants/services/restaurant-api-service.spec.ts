import { TestBed } from '@angular/core/testing';

import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { RestaurantApiService } from './restaurant-api-service';
import { environment } from '../../../../environments/environment';
import { Restaurant } from '../models/restaurants';

describe('RestaurantApiService', () => {
  let service: RestaurantApiService;
  let httpMock: HttpTestingController;

  const apiUrl = environment.apiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting(), RestaurantApiService],
    });
    service = TestBed.inject(RestaurantApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Check no request is forgotten
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all restaurants', () => {
    const mockRestaurants: Restaurant[] = [
      { id: 1, name: 'Test name 1', city: 'Test city 1', category: 'Test category 1' },
      { id: 2, name: 'Test name 2', city: 'Test city 2', category: 'Test category 2' },
    ];

    service.getAll().subscribe((restaurants) => {
      expect(restaurants.length).toBe(2);
      expect(restaurants).toEqual(mockRestaurants);
    });

    const req = httpMock.expectOne(`${apiUrl}/restaurants`);

    expect(req.request.method).toBe('GET');

    req.flush(mockRestaurants);
  });

  it('should fetch restaurant by id', () => {
    const mockRestaurant: Restaurant = {
      id: 1,
      name: 'Test name 1',
      city: 'Test city 1',
      category: 'Test category 1',
    };

    service.getById(1).subscribe((restaurant) => {
      expect(restaurant).toEqual(mockRestaurant);
    });

    const req = httpMock.expectOne(`${apiUrl}/restaurants/1`);

    expect(req.request.method).toBe('GET');

    req.flush(mockRestaurant);
  });

  it('should call correct URL when getById is used', () => {
    service.getById(42).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/restaurants/42`);

    expect(req.request.method).toBe('GET');

    req.flush({});
  });

  it('should handle error response', () => {
    service.getById(666).subscribe({
      error: (err) => {
        expect(err.status).toBe(404);
        expect(err.statusText).toBe('Not Found');
      },
    });

    const req = httpMock.expectOne(`${apiUrl}/restaurants/666`);

    req.flush('Restaurant not found', { status: 404, statusText: 'Not Found' });
  });
});
