import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from '../pages/home/home.component';
import { RegistrationComponent } from '../pages/registration/registration.component';
import { LoginComponent } from '../pages/login/login.component';
import { AdminComponent } from '../admin/admin/admin.component';
import { AuthGuard } from '../guards/auth.guard';


const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LoginComponent, canActivate: [AuthGuard]},
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
 
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class RoutingModule { }
