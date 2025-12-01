import { Address } from './address'; 
import { WorkingHours } from './workingHours'; 
import { Contact } from './contact'; 

export interface RestaurantDTO{
  id: number;
  restaurantName: string;
  isOpen: boolean;
  address: Address;
  workload: number;
}

export interface Restaurant {
  id?: number;
  restaurantName: string;
  isOpen: boolean;
  address: Address;
  averageCustomerRating: number;
  maxOrders: number;
  michelinStar: number;
  kratakOpis: string;
  contact: Contact;
  workingHours: WorkingHours; 
  averageDeliveryTime: string; // tipično: "PT30M"
}

