import { TestBed } from '@angular/core/testing';
import { Restaurant } from '@features/restaurants/models/Restaurants';
import { RestaurantStore } from '@features/restaurants/store/restaurant-store';
import { RestaurantApiService } from '@features/restaurants/services/restaurant-api-service';
import { of, Subject, throwError } from 'rxjs';

describe('RestaurantStore', () => {
  let store: RestaurantStore;
  let api: {
    getAll: ReturnType<typeof vi.fn>;
  };

  const restaurants: Restaurant[] = [
    {
      id: 1,
      name: 'Test name 1',
      city: 'Test city 1',
      category: 'Test category 1',
      latitude: 48.2546,
      longitude: 68.0246,
    },
    {
      id: 2,
      name: 'Test name 2',
      city: 'Test city 2',
      category: 'Test category 2',
      latitude: 68.0246,
      longitude: 48.2546,
    },
  ];

  beforeEach(() => {
    api = {
      getAll: vi.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        RestaurantStore,
        {
          provide: RestaurantApiService,
          useValue: api,
        },
      ],
    });
    store = TestBed.inject(RestaurantStore);
  });

  it('should be created', () => {
    expect(store).toBeTruthy();
  });

  it('should load restaurants', () => {
    api.getAll.mockReturnValue(of(restaurants));

    store.load();

    expect(api.getAll).toHaveBeenCalledTimes(1);
    expect(store.restaurants()).toEqual(restaurants);
    expect(store.loading()).toBe(false);
  });

  it('should set loading to true while waiting for the API', () => {
    const subject = new Subject<Restaurant[]>();

    api.getAll.mockReturnValue(subject);

    store.load();

    expect(store.loading()).toBe(true);

    subject.next(restaurants);
    subject.complete();

    expect(store.loading()).toBe(false);
    expect(store.restaurants()).toEqual(restaurants);
  });

  it('should handle API errors', () => {
    api.getAll.mockReturnValue(throwError(() => new Error('API error')));

    store.load();

    expect(api.getAll).toHaveBeenCalledTimes(1);
    expect(store.loading()).toBe(false);
    expect(store.restaurants()).toEqual([]);
  });

  it('should replace restaurants on subsequent loads', () => {
    api.getAll.mockReturnValueOnce(of(restaurants)).mockReturnValueOnce(
      of([
        {
          id: 3,
          name: 'Test name 3',
          city: 'Test city 3',
          category: 'Test category 3',
        },
      ]),
    );

    store.load();

    expect(store.restaurants()).toEqual(restaurants);

    store.load();

    expect(store.restaurants()).toEqual([
      {
        id: 3,
        name: 'Test name 3',
        city: 'Test city 3',
        category: 'Test category 3',
      },
    ]);
  });
});
