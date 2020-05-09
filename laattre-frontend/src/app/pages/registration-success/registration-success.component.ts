import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-registration-success',
  templateUrl: './registration-success.component.html',
  styleUrls: ['./registration-success.component.css']
})
export class RegistrationSuccessComponent implements OnInit {

  message ="";
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    if(this.route.snapshot.paramMap.get('message')){
      this.message = this.route.snapshot.paramMap.get('message');
    }
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }

}
