import { Component, OnInit } from '@angular/core';
import { RestaurantDTO } from '../../models/restaurant';
import { RestaurantService } from '../../services/restaurant.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css'],
  standalone: false
})
export class RestaurantListComponent implements OnInit {
  restorani: RestaurantDTO[] = [];
  odabraniRestoran?: RestaurantDTO;

  constructor(private restaurantService: RestaurantService, private router: Router) {}

  ngOnInit(): void {
    this.ucitajRestorane();
  }

  ucitajRestorane(): void {
    this.restaurantService.getAll().subscribe(data => {
      this.restorani = data;
    });
  }

  /*
  odaberiRestoran(restoran: RestaurantDTO): void {
    this.odabraniRestoran = restoran;
  }
    */

  prikaziDetalje(id: number): void {
    this.router.navigate(['/restorani', id]);
  }

  getColor(workload: number): string {
    if (workload < 0.4) return 'green';
    if (workload < 0.7) return 'orange';
    return 'red';
  }

  refreshList(): void {
    this.ucitajRestorane();
  }
}
