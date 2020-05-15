import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.css']
})
export class PaymentSuccessComponent implements OnInit {

  message: string;
  currentMessage: string;
  data: any;
  currentData: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dataService: DataService
  ) { }

  ngOnInit() {
    this.dataService.currentMessage.subscribe(message => this.message = message);
    this.dataService.currentData.subscribe(data => this.data = JSON.stringify(data).replace(/[\\\[\]]/g, ''));
    this.currentMessage = localStorage.getItem('currentMessage');
    this.currentData = localStorage.getItem('currentData');
    //.replace(/[\\]/gm, '');
    
    /*let data2='{\"cartItemList\":\"{\"id\":62,\"qty\":1,\"subtotal\":29.98,\"product\":{\"id\":4,\"name\":\"Boubou\",\"createDate\":1586509120000,\"shippingWeight\":100.0,\"listPrice\":40.0,\"ourPrice\":29.98,\"active\":true,\"description\":\"test4\",\"inStockNumber\":3,\"soldNumber\":0,\"productImage\":null,\"type\":\"dress\",\"color\":\"White\",\"sise\":null,\"brand\":\"Kama Radiance\",\"gender\":\"Female\"},\"shoppingCart\":{\"id\":1,\"grandTotal\":29.98},\"order\":null}\",\"estimatedDeliveryDate\":2020,5,14}'
          console.log("data2 cartlist after order:" +JSON.stringify(data2).replace(/[\\\[\]]/g, ''));*/
    /*if(this.route.snapshot.paramMap.get('message')){
      this.message = this.route.snapshot.paramMap.get('message');
    }*/
    
  }

  showOrderDetail(){
    this.router.navigate(['/order-detail', {orderId:this.currentData.slice(11,-1)}]);
  }

}
