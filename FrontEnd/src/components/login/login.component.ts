import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
  const credentials = { username: this.username, password: this.password };
  this.authService.login(credentials).subscribe({
    next: () => this.router.navigate(['/app-home-page']),
    error: () => this.errorMessage = 'Neispravni podaci za prijavu'
  });
}

}
