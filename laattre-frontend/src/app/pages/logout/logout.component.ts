import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(
    private authentocationService: AuthService,
        private router: Router
  ) { }

  ngOnInit() {
    this.authentocationService.logout();
    this.router.navigate(["/home"])
    .then(() => {
      window.location.reload();
    });
  }

}
