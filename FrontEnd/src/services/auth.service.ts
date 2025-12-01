import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { AuthRequestDTO } from '../models/auth-request';
import { JwtResponseDTO } from '../models/jwt-response';
import { jwtDecode } from 'jwt-decode'; 

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080/auth';
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  login(credentials: AuthRequestDTO): Observable<JwtResponseDTO> {
    return this.http.post<JwtResponseDTO>(`${this.API_URL}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem('accessToken', response.accessToken);
        localStorage.setItem('refreshToken', response.token);
        this.loggedIn.next(true);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.loggedIn.next(false);
    // optionally, call backend logout
    // this.http.post(`${this.API_URL}/logout`, {}).subscribe();
  }

  refreshToken(): Observable<JwtResponseDTO> {
    const refreshToken = localStorage.getItem('refreshToken');
    return this.http.post<JwtResponseDTO>(`${this.API_URL}/refreshToken`, { token: refreshToken }).pipe(
      tap(response => {
        localStorage.setItem('accessToken', response.accessToken);
      })
    );
  }

  getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('accessToken');
  }
}
