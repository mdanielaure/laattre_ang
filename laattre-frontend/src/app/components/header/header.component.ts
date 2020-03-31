import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn = false;

  collapsed = true;

  currentUser: User;
  
  constructor(private route: ActivatedRoute,
    private router: Router,
    private loginService: AuthService) { 
      //this.loginService.currentUser.subscribe(x => this.currentUser = x);
    }
    

  ngOnInit() {
    this.isLoggedIn = this.isUserLoggedIn();
    console.log('isLoggedIn ->' + this.isLoggedIn);
  }
  handleLogout() {
    this.loginService.logout();
  }

  isUserLoggedIn(){
    if(this.loginService.currentUserValue){
      return true;
    }
    else{
      return false;
    }
  }

}
