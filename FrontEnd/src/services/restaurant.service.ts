import { Injectable } from '@angular/core';
import { Restaurant, RestaurantDTO } from '../models/restaurant';
import { WorkingHours } from '../models/workingHours';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  private readonly BASE_URL = 'http://localhost:8080/restaurant';

  constructor(private http: HttpClient) {}

  // === Public API ===

  getAll(): Observable<RestaurantDTO[]> {
    return this.http.get<RestaurantDTO[]>(`${this.BASE_URL}/all`);
  }

  getById(id: number): Observable<RestaurantDTO> {
    return this.http.get<RestaurantDTO>(`${this.BASE_URL}/${id}`);
  }

  add(restoran: Restaurant): Observable<any> {
    return this.http.post<any>(`${this.BASE_URL}`, restoran);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/delete/${id}`);
  }

  getWorkingHours(): Observable<WorkingHours[]> {
    return this.http.get<WorkingHours[]>(`${this.BASE_URL}/working-hours`);
  }
}
