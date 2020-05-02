import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from '../pages/home/home.component';
import { RegistrationComponent } from '../pages/registration/registration.component';
import { LoginComponent } from '../pages/login/login.component';
import { AdminComponent } from '../admin/admin/admin.component';
import { AuthGuard } from '../guards/auth.guard';
import { RegistrationSuccessComponent } from '../pages/registration-success/registration-success.component';
import { ProductsComponent } from '../pages/products/products.component';
import { PageNotFoundComponent } from '../components/page-not-found/page-not-found.component';
import { ProductDetailsComponent } from '../pages/product-details/product-details.component';
import { CheckoutComponent } from '../pages/checkout/checkout.component';
import { LogoutComponent } from '../pages/logout/logout.component';
import { ShoppingCartComponent } from '../pages/shopping-cart/shopping-cart.component';
import { AddToCartComponent } from '../pages/add-to-cart/add-to-cart.component';
import { HeaderComponent } from '../components/header/header.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'registration-success', component: RegistrationSuccessComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LogoutComponent, canActivate: [AuthGuard]},
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },

  { path: 'products', component: ProductsComponent},
  { path: 'products/:page/:size', component: ProductsComponent},
  { path: 'product-details/:id', component: ProductDetailsComponent},
  { path: 'product-details', component: ProductDetailsComponent},
  { path: 'cart', component: ShoppingCartComponent, canActivate: [AuthGuard]},
  { path: 'add-to-cart/:pId/:qty/:username', component: AddToCartComponent, canActivate: [AuthGuard]},
  { path: 'checkout/:cartId/:username', component: CheckoutComponent, canActivate: [AuthGuard]},


  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
 
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
