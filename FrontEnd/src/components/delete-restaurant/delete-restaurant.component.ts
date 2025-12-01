import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RestaurantDTO } from '../../models/restaurant';
import { RestaurantService } from '../../services/restaurant.service';

@Component({
  selector: 'app-delete-restaurant',
  template: `
    <button (click)="obrisi()">Obriši</button>
  `
})
export class DeleteRestaurantComponent {
  @Input() restoranId!: number;
  @Output() restoranObrisan = new EventEmitter<void>();

  constructor(private restaurantService: RestaurantService) {}

  obrisi() {
    this.restaurantService.delete(this.restoranId).subscribe({
      next: () => {
        console.log('Restoran obrisan!');
        this.restoranObrisan.emit(); // signal to parent to refresh list
      },
      error: (err) => console.error('Greška pri brisanju:', err)
    });
  }
}
