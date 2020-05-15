import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';
import { Observable } from 'rxjs';
import { config } from 'src/app/config';
import { map } from 'rxjs/operators';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';


@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: any;
  pageNumbers: any;
  MyError: any;
  oneDay = 24 * 60 * 60 * 1000;
  page = '1';
  size = '6';
  currentUser: any;
  

  constructor(
    private productService: ProductService,
    private shoppingCartService: ShoppingCartService,
    private route: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit() {
    /*this.productService.getAll().subscribe(data => {
      //console.log('this.products ->' + JSON.stringify(data));
      this.products = data;
     
    });*/
    //this.reloadData();
    this.route.paramMap
    // (+) converts string 'id' to a number

    .subscribe((params) => {
      this.page = params.get('page') != null ? this.page = params.get('page') : this.page = '1';
      this.size = params.get('size') != null ? this.size = params.get('size') : this.size = '6';
      console.log("page " + this.page);
      console.log("size " + this.size);
      this.getter(this.page,this.size);
    });

    
    if(localStorage.getItem('currentUser')){
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }
    
  }

  reloadData() {
    //this.products = this.productService.getAll();
  }

  getter(page: string, size: string) {
    this.productService.getAll(page, size)
    .pipe(
      map((data)=>{
        return data;
      })
    ).subscribe(
      (data: any) => {
        this.products = data.productPage;
        this.pageNumbers = data.pageNumbers;
        console.log(this.pageNumbers);
        console.log(this.products);
    },
    (error) => {
      this.MyError = error;
      console.log('error: ' + this.MyError);
    });
  }

  getImage(id: any){
    return this.productService.getImage(id);
  }

  isNewProduct(createDate){
    return this.productService.isNewProduct(createDate);
  }

  addToCart(pId: string, qty: string){
    
   if(!this.currentUser){
    this.router.navigate(['login']);
   } 
    this.router.navigate(['add-to-cart', pId, qty, this.currentUser.user.email]);

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
