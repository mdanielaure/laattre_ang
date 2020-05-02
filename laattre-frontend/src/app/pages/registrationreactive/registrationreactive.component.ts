import { Component, OnInit } from '@angular/core';
//import { FormGroup, FormControl } from '@angular/forms'; /** import formbuilder instead */
import { FormBuilder, Validator, Validators } from '@angular/forms';
import { forbiddenNameValidator } from 'src/app/shared/user-name.validator';
import { MatchPasswordValidator } from 'src/app/shared/matching-password.validator';


@Component({
  selector: 'app-registrationreactive',
  templateUrl: './registrationreactive.component.html',
  styleUrls: ['./registrationreactive.component.css']
})
export class RegistrationreactiveComponent implements OnInit {

  //comment this to replace by formbuilder service
  /*registrationForm = new FormGroup({
    username: new FormControl('Danie'),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
    address: new FormGroup({
      country: new FormControl('France'),
      city: new FormControl(''),
      state: new FormControl(''),
      postalCode: new FormControl('')
    })

  });*/

  registrationForm = this.fb.group({
    username: ['', [Validators.required, forbiddenNameValidator(/passworD/)]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirmPassword: [''],
    address: this.fb.group({
      country: [''],
      city: [''],
      state: [''],
      postalCode: ['']
    })

  }, {validators: MatchPasswordValidator});

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  get f() { return this.registrationForm.controls; }

  loadApiData(){
    this.registrationForm.patchValue({ 
      username: "Danie Laure", 
      password: "test", 
      confirmPassword: "test", 
      address: { 
        country: "France", 
        city: "Blois", 
        state: "Loir-et-Cher", 
        postalCode: "41000" 
      } 
    });
  }

}
