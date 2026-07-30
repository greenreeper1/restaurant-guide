import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantListPage } from '@features/restaurants/pages/restaurant-list-page/restaurant-list-page';
import { Restaurant } from '@features/restaurants/models/Restaurants';
import { signal } from '@angular/core';
import { RestaurantStore } from '@features/restaurants/store/restaurant-store';
import { By } from '@angular/platform-browser';
import { RestaurantCard } from '@features/restaurants/components/restaurant-card/restaurant-card';

describe('RestaurantListPage', () => {
  let component: RestaurantListPage;
  let fixture: ComponentFixture<RestaurantListPage>;

  const restaurants = signal<Restaurant[]>([
    {
      id: 1,
      name: 'Test name 1',
      city: 'Test city 1',
      category: 'Test category 1',
      latitude: 68.0246,
      longitude: 48.2546,
    },
    {
      id: 2,
      name: 'Test name 2',
      city: 'Test city 2',
      category: 'Test category 2',
      latitude: 48.2546,
      longitude: 68.0246,
    },
  ]);

  const loading = signal(false);

  const storeMock = {
    restaurants,
    loading,
    load: vi.fn(),
  };

  beforeEach(async () => {
    storeMock.load.mockClear();
    restaurants.set([
      {
        id: 1,
        name: 'Test name 1',
        city: 'Test city 1',
        category: 'Test category 1',
        latitude: 68.0246,
        longitude: 48.2546,
      },
      {
        id: 2,
        name: 'Test name 2',
        city: 'Test city 2',
        category: 'Test category 2',
        latitude: 48.2546,
        longitude: 68.0246,
      },
    ]);
    loading.set(false);

    await TestBed.configureTestingModule({
      imports: [RestaurantListPage],
      providers: [
        {
          provide: RestaurantStore,
          useValue: storeMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RestaurantListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load restaurants on init', () => {
    expect(storeMock.load).toHaveBeenCalledTimes(1);
  });

  it('should display loading message', () => {
    loading.set(true);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Chargement...');
  });

  it('should hide loading message when data is loaded', () => {
    loading.set(false);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).not.toContain('Chargement...');
  });

  it('should display one RestaurantCard per restaurant', () => {
    loading.set(false);
    fixture.detectChanges();

    const cards = fixture.debugElement.queryAll(By.directive(RestaurantCard));

    expect(cards.length).toBe(2);
  });

  it('should display no card when there are no restaurants', () => {
    restaurants.set([]);
    loading.set(false);
    fixture.detectChanges();

    const cards = fixture.debugElement.queryAll(By.directive(RestaurantCard));

    expect(cards.length).toBe(0);
  });
});
