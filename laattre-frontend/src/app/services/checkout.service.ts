import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  constructor(private http: HttpClient) { }


  checkout(cartId: any, username:any): Observable<any>{
    return this.http.get(`${config.apiUrl}/checkout`, {params:{cartId, username}});
  }

  addToCart(pId: any, qty: any, username: any){
    var body = "pId="+pId+"&qty="+qty+"&username="+username;
    let headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    return this.http.post(`${config.apiUrl}/shoppingCart/addItem`, body , {headers});
  }
}
