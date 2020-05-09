import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-add-to-cart',
  templateUrl: './add-to-cart.component.html',
  styleUrls: ['./add-to-cart.component.css']
})
export class AddToCartComponent implements OnInit {

  pId: any;
  qty: any;
  user: any;
  result: any;
  loading = false;

  constructor(
    private shoppingCartService: ShoppingCartService,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe((params) => {
      this.pId = params.get('pId');
      this.qty = params.get('qty');
      this.user = params.get('username');
      this.addToCart(this.pId, this.qty, this.user);
    });
  }

  addToCart(pId: any, qty: any, username: any){
    this.alertService.clear();
    this.loading = true;
    this.shoppingCartService.addToCart(pId, qty, username)
    .pipe(
      map((data)=>{
       return data;
      })
    ).subscribe(
      (data: any) => {
        this.result = data.addProductSuccess;
        console.log("cart "+JSON.stringify(data));
        if(data.addProductSuccess){
          this.alertService.success('Successfully added to cart', true);
          this.router.navigate(['cart']).then(() => {
            window.location.reload();
          });
        }
        else if (data.notEnoughStock){
          this.alertService.error("Oops, some of the products don't have enough stock. Please update product quantity", true);
        }
       
        /*this.router.navigate(['cart'])
                    .then(() => {
                        window.location.reload();
                      });*/
    },
    (error) => {
      this.alertService.error(error);
      this.loading = false;
    });
    
  }

}
