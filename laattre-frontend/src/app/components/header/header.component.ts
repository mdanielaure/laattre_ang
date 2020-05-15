import { Component, OnInit, Inject, LOCALE_ID } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';
import { ShoppingCart } from 'src/app/models/shoppingCart';
import { Observable } from 'rxjs';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';
import { map } from 'rxjs/operators';
import { ProductService } from 'src/app/services/product.service';
import { AlertService } from 'src/app/services/alert.service';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  languages = [
    { code: 'en', label: 'English'},
    { code: 'fr', label: 'Français'}
  ];

  isLoggedIn = false;

  collapsed = true;

  currentUser: any;

  cart: any;

  cartItemList: any;

  emptyCart: boolean = true;
  
  constructor(private route: ActivatedRoute,
    private router: Router,
    private loginService: AuthService,
    private shoppingCartService: ShoppingCartService,
    private productService: ProductService,
    private alertService: AlertService,
    //@Inject(LOCALE_ID) public localeId: string
    public translate: TranslateService
    ) { 
      translate.addLangs(['en', 'fr']);
      translate.setDefaultLang('en');
      const browserland = translate.getBrowserLang();
      translate.use(browserland.match(/en|fr/) ? browserland : 'en');


      //this.loginService.currentUser.subscribe(x => this.currentUser = x);
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
      //console.log('currentUser ->' +  JSON.parse(localStorage.getItem('currentUser')));
    }
    

  ngOnInit() {
    this.isLoggedIn = this.isUserLoggedIn();
    console.log('isLoggedIn ->' + this.isLoggedIn);
    if( this.isLoggedIn){
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
      this.getCart(this.currentUser.user.email);
    }
  }

  handleLogout() {
    this.loginService.logout();
    this.router.navigate(["/home"])
    .then(() => {
      window.location.reload();
    });
  }

  isUserLoggedIn(){
    if(localStorage.getItem('currentUser')){
      return true;
    }
    else{
      return false;
    }
  }

  getCart(username: string) {
    this.alertService.clear();
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
      this.alertService.error(error);
    });
  }

  getImage(id: any){
    return this.productService.getImage(id);
  }

  deleteItem(id: any){
    this.alertService.clear();
    this.shoppingCartService.deleteItem(id).pipe(
      map((data)=>{
      return data;
      })
    ).subscribe(
      (data: any) => {
        
        console.log('Delete product ' + data);
    },
    (error) => {
      this.alertService.error(error);
    });

    this.ngOnInit();
    //this.router.navigateByUrl('/header', { skipLocationChange: true }).then(() => {
      this.router.navigate(['cart']);
    //});

  }

  checkout(cartId: any){
    
    if(!this.currentUser){
     this.router.navigate(['login']);
    } 
     this.router.navigate(['checkout', cartId, this.currentUser.user.email]);
     
   }

   changeLang(lang:string){
    this.translate.use(lang);
   }

}
