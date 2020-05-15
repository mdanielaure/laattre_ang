import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { UserService } from 'src/app/services/user.service';
import { map } from 'rxjs/operators';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  cart: any;
  currentUser: any;
  cartItemList: any;
  orderId: any;
  order:any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private userService: UserService,
    private productService: ProductService
  ) { }

  ngOnInit() {

    if(this.route.snapshot.paramMap.get('orderId')){
      this.orderId = this.route.snapshot.paramMap.get('orderId');
    }
    
    if(localStorage.getItem('currentUser')){
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    this.getOrderDetail(this.orderId, this.currentUser.user.email); 
  }

  getOrderDetail(orderId: any, userEmail: string){
    this.alertService.clear();
    this.userService.getOrderDetail(orderId, userEmail)
    .pipe(
      map((data)=>{
        //data.productPage.content.createDate = new Date(data.productPage.content.createDate);
        //data.productPage.content.ourPrice = new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(data.productPage.content.ourPrice);
        return data;
      })
    ).subscribe(
      (data: any) => {

        console.log("orderData: "+ JSON.stringify(data));

        //if(!data.badRequest){
          this.order = data.order;
          this.cartItemList = data.cartItemList;
          console.log("order: "+ JSON.stringify(this.order));
        //}
        
    },
    (error) => {
      console.log('error: ' + error);
      this.alertService.error(error);
    });
  }

  getImage(id: any){
    return this.productService.getImage(id);
  }

}
