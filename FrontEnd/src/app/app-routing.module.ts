import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RestaurantListComponent } from '../components/restaurant-list/restaurant-list.component';
import { HomePageComponent } from '../components/home-page/home-page.component';
import { RestaurantDetailsComponent } from '../components/restaurant-details/restaurant-details.component';
import { LoginComponent } from '../components/login/login.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'app-login', component: LoginComponent },
  { path: '', component: HomePageComponent },
  { path: 'app-home-page', component: HomePageComponent },
  { path: 'app-restaurant-list', component: RestaurantListComponent },
  { path: 'restorani/:id', component: RestaurantDetailsComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
