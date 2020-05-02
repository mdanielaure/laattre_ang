import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

productId: any;
product: any;
MyError: any;
qtyValue: number=1;
currentUser: any;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.paramMap
    // (+) converts string 'id' to a number

    .subscribe((params) => {
      this.productId = params.get('id');
      console.log("id " + this.productId);
      this.getter(this.productId);
    });

    if(localStorage.getItem('currentUser')){
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

  }

  getter(id :any) {
    this.productService.getById(id)
    .pipe(
      map((data)=>{
        //data.productPage.content.createDate = new Date(data.productPage.content.createDate);
        //data.productPage.content.ourPrice = new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(data.productPage.content.ourPrice);
        return data;
      })
    ).subscribe(
      (data: any) => {
        this.product = data.product;
        
        console.log(this.product);
    },
    (error) => {
      this.MyError = error;
      console.log('error: ' + this.MyError);
    });
  }

  isNewProduct(createDate){
    return this.productService.isNewProduct(createDate);
  }

  getImage(id: number){
    return this.productService.getImage(id);
  }

  addToCart(pId: string){
    
    if(!this.currentUser){
     this.router.navigate(['login']);
    } 
     this.router.navigate(['add-to-cart', pId, this.qtyValue, this.currentUser.user.email]);
 
     /*this.shoppingCartService.addToCart(pId, qty, username)
     .pipe(
       map((data)=>{
        return data;
       })
     ).subscribe(
       (data: any) => {
         //console.log("cart "+JSON.stringify(data));
     },
     (error) => {
       this.MyError = error;
       console.log('error: ' + this.MyError);
     });*/
     
   }

}
