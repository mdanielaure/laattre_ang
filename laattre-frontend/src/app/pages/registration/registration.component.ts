import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { AlertService } from 'src/app/services/alert.service';
import { MatchPasswordValidator } from 'src/app/shared/matching-password.validator';
import { EmailValidator } from 'src/app/shared/email.validator';
import { patternValidator } from 'src/app/shared/custom.validator';
import { config } from 'src/app/config';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

    registrationForm: FormGroup; 
    loading = false;
    isSubmitted = false;
    appnUrl = `${config.clientUrl}/registration-confirm`
    


  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private userService: UserService,
      private alertService: AlertService,
      private fb: FormBuilder
  ) {
      // redirect to home if already logged in
      /*if (this.authenticationService.currentUserValue) {
          this.router.navigate(['/']);
      }*/
  }

 


  ngOnInit() {
     /* this.registerForm = this.formBuilder.group({
          firstName: ['', Validators.required],
          lastName: ['', Validators.required],
          password: ['', [Validators.required, Validators.minLength(6)]],
          email: ['', Validators.required]
          
          //matchingPassword: ['', [Validators.required, Validators.minLength(6)]]
      });*/

      this.registrationForm = this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        password: ['', [Validators.required, Validators.pattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&]).{8,30})")]],
        
       /* password: [null, Validators.compose([
            // 1. Password Field is Required
            //Validators.required,
            // 2. check whether the entered password has a number
            //patternValidator(/\d/, { hasNumber: true }),
             // 3. check whether the entered password has upper case letter
           // patternValidator(/[A-Z]/, { hasCapitalCase: true }),
            // 4. check whether the entered password has a lower-case letter
            //patternValidator(/[a-z]/, { hasSmallCase: true }),
            // 5. check whether the entered password has a special character
            //patternValidator(/[!@#$%^&]/, { hasSpecialCharacters: true }),
            // 6. Has a minimum length of 8 characters
            Validators.minLength(8)
            
            ])
        ],*/
        
        email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
        //email: ['', [Validators.required, EmailValidator]],
        confirmPassword: [''],
        returnUrl: [this.appnUrl]
    }, {validators: MatchPasswordValidator});
  }

  // convenience getter for easy access to form fields
  get f() { return this.registrationForm.controls; }

  onSubmit() {
      this.isSubmitted = true;

      // reset alerts on submit
      this.alertService.clear();

      // stop here if form is invalid
      if (this.registrationForm.invalid) {
          return;
      }

      this.loading = true;
      this.userService.register(this.registrationForm.value)
          .pipe(first())
          .subscribe(
              data => {
                  this.alertService.success('Registration successful', true);
                  this.router.navigate(['/registration-success', {message:'success'}]);
              },
              error => {
                  this.alertService.error(error);
                  this.loading = false;
              });
  }


}
