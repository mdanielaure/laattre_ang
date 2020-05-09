import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { ShoppingCart } from 'src/app/models/shoppingCart';
import { User } from 'src/app/models/user';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';
import { map } from 'rxjs/operators';
import { ProductService } from 'src/app/services/product.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  
  cart: any;
  currentUser: any;
  cartItemList: any;
  emptyCart: boolean = true;
  errorMassage = "";

  constructor(
    private shoppingCartService: ShoppingCartService,
    private   productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {

    if(localStorage.getItem('currentUser')){
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
      this.getCart(this.currentUser.user.email); 
    } 

    console.log("errorMessage "+this.route.snapshot.paramMap.get('errorMessage'));
    if(this.route.snapshot.paramMap.get('errorMessage')){
      this.errorMassage = this.route.snapshot.paramMap.get('errorMessage');
      this.alertService.error(this.errorMassage);
      
    }
    
                        
  }

  getCart(username: string) {
    this.shoppingCartService.getCart(username)
    .pipe(
      map((data)=>{
        //data.productPage.content.createDate = new Date(data.productPage.content.createDate);
        //data.productPage.content.ourPrice = new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(data.productPage.content.ourPrice);
        return data;
      })
    ).subscribe(
      (data: any) => {
        if(!data.emptyCart){
          //this.cart = data.shoppingCart;
          //this.cartItemList = data.cartItemList;
          this.emptyCart = data.emptyCart;
          this.cartItemList = data.cartItemList;
          this.cart = data.shoppingCart;
          //localStorage.setItem('cart', JSON.stringify(data.shoppingCart));
          //localStorage.setItem('cartItemList', JSON.stringify(data.cartItemList));
        }
        //console.log('emptyCart ' + this.emptyCart);
        //console.log('cartItemList ' + this.cartItemList);
        //console.log('shoppingCart ' + this.cart);
        //console.log(this.pageNumbers);
        //console.log(this.products);
    },
    (error) => {
      console.log('error: ' + error);
    });
  }

  getImage(id: number){
    return this.productService.getImage(id);
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
    
    this.router.navigateByUrl('/header', { skipLocationChange: true }).then(() => {
      this.router.navigate(['cart']);
    });
  }

  checkout(cartId: any){
    
    if(!this.currentUser){
     this.router.navigate(['login']);
    } 
     this.router.navigate(['checkout', cartId, this.currentUser.user.email]);
     
   }

}
