import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { AlertService } from 'src/app/services/alert.service';
import { map } from 'rxjs/operators';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { config } from 'src/app/config';
import { DataService } from 'src/app/services/data.service';

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
  
  allForms: FormGroup;
  shippingAddress: FormGroup;
  billingAddress: FormGroup;
  shippingMethod: FormGroup;
  paymentMethod: FormGroup;
  isOptional = false;
  firstNextClicked = false;
  secondNextClicked = false;
  tirdNextClicked = false;
  fourthNextClicked = false;
  billingSameAsShipping = false;

  message: string;
  currentMessage: string;
  data: any;
  currentData: any;


  constructor(
    private productService: ProductService,
    private checkoutService: CheckoutService, 
    private shoppingCartService: ShoppingCartService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private _formBuilder: FormBuilder,
    private dataService: DataService
    ) {

  }

  ngOnInit() {

    this.dataService.currentMessage.subscribe(message => this.message = message);
    this.dataService.currentData.subscribe(data => this.data = data);

    this.currentMessage = localStorage.getItem('currentMessage');
    this.currentData = localStorage.getItem('currentData');

    if(localStorage.getItem('currentUser')){

      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));

      this.route.paramMap.subscribe((params) => {
      this.cartId = params.get('cartId');
      
      this.checkout(this.cartId, this.currentUser.user.email);
    });

    } 

    this.allForms = this._formBuilder.group({
      shippingAddress: this._formBuilder.group({
        shippingAddressFirstName: ['', Validators.required],
        shippingAddressLastName: ['', Validators.required],
        shippingAddressStreet1: ['', Validators.required],
        shippingAddressStreet2: [''],
        shippingAddressCountry: ['', Validators.required],
        shippingAddressState: ['', Validators.required],
        shippingAddressCity: ['', Validators.required],
        shippingAddressZipCode: ['', Validators.required],
        shippingAddressPhone:['', Validators.required]  
      }),
      billingAddress: this._formBuilder.group({
        billingAddressFirstName: ['', Validators.required],
        billingAddressLastName: ['', Validators.required],
        billingAddressStreet1: ['', Validators.required],
        billingAddressStreet2: [''],
        billingAddressCountry: ['', Validators.required],
        billingAddressState: ['', Validators.required],
        billingAddressCity: ['', Validators.required],
        billingAddressZipCode: ['', Validators.required],
        billingAddressPhone:['', Validators.required] 
      }),
      shippingMethod: this._formBuilder.group({
        shippingMethod: ['', Validators.required]
      }),
  
      paymentMethod: this._formBuilder.group({
        type: ['', Validators.required]
      }),

      username:[this.currentUser.user.email]
    });

    this.shippingAddress = this._formBuilder.group({
      shippingAddressFirstName: ['', Validators.required],
      shippingAddressLastName: ['', Validators.required],
      shippingAddressStreet1: ['', Validators.required],
      shippingAddressStreet2: [''],
      shippingAddressCountry: ['', Validators.required],
      shippingAddressState: ['', Validators.required],
      shippingAddressCity: ['', Validators.required],
      shippingAddressZipCode: ['', Validators.required],
      shippingAddressPhone:['', Validators.required]  
    });

    this.billingAddress = this._formBuilder.group({
      billingAddressFirstName: ['', Validators.required],
      billingAddressLastName: ['', Validators.required],
      billingAddressStreet1: ['', Validators.required],
      billingAddressStreet2: [''],
      billingAddressCountry: ['', Validators.required],
      billingAddressState: ['', Validators.required],
      billingAddressCity: ['', Validators.required],
      billingAddressZipCode: ['', Validators.required],
      billingAddressPhone:['', Validators.required] 
    });

    this.shippingMethod = this._formBuilder.group({
      shippingMethod: ['', Validators.required]
    });

    this.paymentMethod = this._formBuilder.group({
      type: ['', Validators.required]
    });

    
                        
  }

  get f() { return this.allForms['controls'].shippingAddress['controls']; }
  get f1() { return this.shippingAddress.controls; }
  get f2() { return this.billingAddress.controls; }
  get f3() { return this.shippingMethod.controls; }
  get f4() { return this.paymentMethod.controls; }

  checkout(cartId: any, username: any){
    this.checkoutService.checkout(cartId, username).pipe(
      map((data)=>{
       return data;
      })
    ).subscribe(
      (data: any) => {
        //console.log("data "+JSON.stringify(data));
        if(!data.notEnoughStock){
          this.cartItemList = data.cartItemList;
        this.emptyCart = data.emptyCart;
        this.cart = data.shoppingCart;
        if(this.emptyCart){
          this.router.navigate(['/cart']);
        }
        }
        else{
          this.alertService.error("Oops, some of the products don't have enough stock. Please update product quantity", true);
          this.router.navigate(['/cart', {errorMessage:"Oops, some of the products don't have enough stock. Please update product quantity"}]);
        }
        
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
      console.log('error: ' + error);
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
      this.alertService.error(error);
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
      this.alertService.error(error);
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

  setfirstNextClick(){
    this.firstNextClicked = true;
  }
  setsecondNextClick(){
    this.secondNextClicked = true;
  }
  settirdNextClick(){
    this.tirdNextClicked = true;
  }
  setfourthNextClick(){
    this.fourthNextClicked = true;
  }

  setSameAddress(){
    this.billingSameAsShipping = true;
    this.billingAddress.disable();
  }
  
  placeOrder(){
    if(`${config.mock}`){
      console.log("mock : "+`${config.mock}`);
      this.checkoutService.placeOrder(this.shippingAddress.value, this.billingAddress.value, this.paymentMethod.value, this.billingSameAsShipping, this.shippingMethod.get('shippingMethod').value, this.currentUser.user.email)
      .pipe(
        map((data)=>{
          data = JSON.stringify(data);
         // data = JSON.stringify(data).replace(/\\/g, '')
        return data;
        })
      )
      .subscribe(
        data => {
            console.log("data cartlist after order:" +data);
            this.alertService.success('Payement successful', true);
            this.dataService.changeMessage('Payment success. We will send you a confirmation email');
            this.currentMessage = localStorage.getItem('currentMessage');
            this.dataService.changeData(data);
            this.currentData = localStorage.getItem('currentData');
            this.router.navigate(['/payment-success']).then(() => {
              //window.location.reload();
            });
        },
        error => {
            this.alertService.error(error);
        });
    }
  }
}
