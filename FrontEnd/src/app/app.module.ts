import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RestaurantListComponent } from '../components/restaurant-list/restaurant-list.component';
import { RestaurantDetailsComponent } from '../components/restaurant-details/restaurant-details.component';
import { RouterModule } from '@angular/router';
import { HomePageComponent } from '../components/home-page/home-page.component';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { AddRestaurantComponent } from '../components/add-restaurant/add-restaurant.component';
import { LoginComponent } from '../components/login/login.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from '../interceptor/auth.interceptor';
import { CommonModule } from '@angular/common';
import { DeleteRestaurantComponent } from '../components/delete-restaurant/delete-restaurant.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AddRestaurantComponent,
    RestaurantDetailsComponent,
    RestaurantListComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule,
    BrowserModule,
    FormsModule,
    CommonModule,
    HomePageComponent,
    DeleteRestaurantComponent
    
  ],
 providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
