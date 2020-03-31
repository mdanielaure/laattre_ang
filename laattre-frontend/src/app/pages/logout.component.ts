
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout'

})
export class LayoutComponent implements OnInit {

    constructor(
        private authentocationService: AuthService,
        private router: Router)  { }

  ngOnInit() {
      this.authentocationService.logout();
      this.router.navigate(["/home"])
  }

}
