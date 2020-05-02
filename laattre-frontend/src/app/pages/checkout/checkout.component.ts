import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { AlertService } from 'src/app/services/alert.service';
import { map } from 'rxjs/operators';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  currentUser: any;
  cartId: any;
  cartItemList: any;
  cart: any;
  emptyCart: boolean = true;

  constructor(
    private productService: ProductService,
    private checkoutService: CheckoutService, 
    private shoppingCartService: ShoppingCartService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router
    ) {

  }

  ngOnInit() {

    if(localStorage.getItem('currentUser')){

      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));

      this.route.paramMap.subscribe((params) => {
      this.cartId = params.get('cartId');
      
      this.checkout(this.cartId, this.currentUser.user.email);
    });

       

    } 
                        
  }

  checkout(cartId: any, username: any){
    this.checkoutService.checkout(cartId, username).pipe(
      map((data)=>{
       return data;
      })
    ).subscribe(
      (data: any) => {
        console.log("data "+JSON.stringify(data));
        this.cartItemList = data.cartItemList;
        this.emptyCart = data.emptyCart;
        this.cart = data.shoppingCart;
        /*if(data.addProductSuccess){
          this.alertService.success('Successfully added to cart', true);
          this.router.navigate(['cart']).then(() => {
            window.location.reload();
          });
        }
        else if (data.notEnoughStock){
          this.alertService.error("Oops, some of the products don't have enough stock. Please update product quantity", true);
        }*/
       
        /*this.router.navigate(['cart'])
                    .then(() => {
                        window.location.reload();
                      });*/
    },
    (error) => {
      this.alertService.error(error);
    });
  }

  deleteItem(id: any){
    this.shoppingCartService.deleteItem(id).pipe(
      map((data)=>{
      return data;
      })
    ).subscribe(
      (data: any) => {
        
        console.log('Delete product ' + data);
    },
    (error) => {
      console.log('error: ' + error);
    });
    
    this.router.navigateByUrl('/header', { skipLocationChange: true }).then(() => {
      this.router.navigate(['cart']);
    });
  }

  updateCartItemQty(event, cartItemId: any){

    this.shoppingCartService.updateCartItemQty(cartItemId, event.target.value).pipe(
      map((data)=>{
      return data;
      })
    ).subscribe(
      (data: any) => {
        
        console.log('Update cart item qty ' + data);
    },
    (error) => {
      console.log('error: ' + error);
    });
    
    let nextUrl = window.location;
    this.router.navigate(['cart']).then(() => {
      
      this.router.navigate([nextUrl]);
      //window.location.reload();
    });
  }

  getImage(id: number){
    return this.productService.getImage(id);
  }

}
