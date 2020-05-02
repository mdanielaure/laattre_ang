import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../config';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private http: HttpClient) { }

  getCart(username:string): Observable<any> {
    return this.http.get(`${config.apiUrl}/shoppingCart/cart`, { params:{ username:username} });
  }

  deleteItem(id: any){
    let headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    return this.http.post(`${config.apiUrl}/shoppingCart/removeItem`, 'id='+id, {headers});
  }

  addToCart(pId: any, qty: any, username: any){
    var body = "pId="+pId+"&qty="+qty+"&username="+username;
    let headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    return this.http.post(`${config.apiUrl}/shoppingCart/addItem`, body , {headers});
  }

  updateCartItemQty(cartItemId: any, qty: any){
    var body = "id="+cartItemId+"&qty="+qty;
    let headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    return this.http.post(`${config.apiUrl}/shoppingCart/updateCartItem`, body , {headers});
  }
}




