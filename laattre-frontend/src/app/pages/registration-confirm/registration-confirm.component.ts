import { Component, OnInit } from '@angular/core';
import { Router, Route, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { map } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {

 confirmMessage: any
 expired: boolean = false;
 ivalidToken: boolean = false;
 token: any;

  constructor(
    private alertService: AlertService,
    private router: Router,
     private route: ActivatedRoute,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.route.queryParams
    .subscribe((params) => {
      this.token = params.token;
      this.confirmRegistration(this.token);
    });
  }

  confirmRegistration(token: any){
    this.userService.confirmRegistration(token)
    .pipe(
      map((data)=>{
        return data;
      })
    ).subscribe(
      (data: any) => {
        this.confirmMessage = data.message;
        if(data.expired){
          this.expired=data.expired;
        }
        if(/Invalid/.test(this.confirmMessage)){
          this.ivalidToken = true;
        }
    },
    (error) => {
      this.alertService.error(error);
    });
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }

  resendToken(){
    this.userService.resendToken(this.token)
    .subscribe(
      (data: any) => {
        this.confirmMessage = data.message;
        console.log("data.message " +data.message);
        this.expired = false;
        this.ivalidToken = false;
      },
      (error) => {
        this.alertService.error(error);
      }
    );

    this.router.navigateByUrl('/header', { skipLocationChange: true }).then(() => {
      this.router.navigate(['registration-confirm']);
    });

  }

}
