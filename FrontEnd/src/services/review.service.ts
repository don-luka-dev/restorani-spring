import { Injectable } from '@angular/core';
import { Review } from '../models/review';
import { WorkingHours } from '../models/workingHours';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private readonly BASE_URL = 'http://localhost:8080/review';

  constructor(private http: HttpClient) {}

  // === Public API ===

  getAll(): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.BASE_URL}/all`);
  }

  getByRestaurantId(id: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.BASE_URL}/restaurantId/${id}`);
  }
  
}
