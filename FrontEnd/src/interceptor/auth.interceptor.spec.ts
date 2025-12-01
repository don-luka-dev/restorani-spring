import { TestBed } from '@angular/core/testing';
import { HttpInterceptorFn } from '@angular/common/http';

import { AuthInterceptor } from './auth.interceptor';
import { Observable } from 'rxjs';

describe('AuthInterceptor', () => {
  const mockHandler: import('@angular/common/http').HttpHandler = {
    handle: (req) => {
      // You can add more logic here if needed for your tests
      return new Observable();
    }
  };

  const interceptor: HttpInterceptorFn = (req, next) =>
    TestBed.runInInjectionContext(() => new AuthInterceptor().intercept(req, mockHandler));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });
});
