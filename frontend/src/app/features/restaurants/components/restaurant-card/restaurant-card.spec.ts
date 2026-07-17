import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantCard } from './restaurant-card';
import { Restaurant } from '../../models/Restaurants';
import { By } from '@angular/platform-browser';

describe('RestaurantCard', () => {
  let component: RestaurantCard;
  let fixture: ComponentFixture<RestaurantCard>;

  const mockRestaurant: Restaurant = {
    id: 1,
    name: 'Test name 1',
    city: 'Test city 1',
    category: 'Test category 1',
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RestaurantCard],
    }).compileComponents();

    fixture = TestBed.createComponent(RestaurantCard);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('restaurant', mockRestaurant);
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the restaurant name', () => {
    const title = fixture.debugElement.query(By.css('h2'));

    expect(title.nativeElement.textContent.trim()).toBe('Test name 1');
  });

  it('should display the restaurant city', () => {
    const paragraphs = fixture.debugElement.queryAll(By.css('p'));

    expect(paragraphs[0].nativeElement.textContent.trim()).toBe('Test city 1');
  });

  it('should display the restaurant category', () => {
    const paragraphs = fixture.debugElement.queryAll(By.css('p'));

    expect(paragraphs[1].nativeElement.textContent.trim()).toBe('Test category 1');
  });

  it('should render the card container', () => {
    const card = fixture.debugElement.query(By.css('.card'));

    expect(card).toBeTruthy();
  });
});
