import { Injectable } from '@angular/core';
import { config } from '../config';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getAll(page:string, size: string, menu: string, categoryId: any): Observable<any> {
    return this.http.get(`${config.apiUrl}/product/productList`, { params:{ page, size, menu, categoryId } });
  }

  getById(id: any){
    return this.http.get(`${config.apiUrl}/product/productInfo`, { params:{ id:id} });
  }

  isNewProduct(createDate){
    const oneDay = 24 * 60 * 60 * 1000;
    const date1 = new Date(createDate);
    //const secondDate = new Date(2008, 1, 22);
    //console.log('date:'+ date1)
    //console.log('date: ' + Math.round(Math.abs((date1.getTime()  - new Date().getTime() ) / oneDay)));
    return Math.round(Math.abs((date1.getTime()  - new Date().getTime() ) / oneDay)) < 30;
  }

  getImage(id: any){
    return `${config.apiUrl}/resources/static/image/product/`+id+".png";
  }

}
