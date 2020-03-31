import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
 	
import { MaterialModule } from './material/material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { HttpInterceptorService } from './services/http-interceptor.service';
import { ErrorInterceptor } from './services/error.interceptor';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SliderComponent } from './components/slider/slider.component';
import { CollectionsComponent } from './components/collections/collections.component';
import { LatestComponent } from './components/latest/latest.component';
import { from } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderMenuComponent } from './components/header-menu/header-menu.component';
import { LayoutComponent } from './components/layout/layout.component';
import { HomeComponent } from './pages/home/home.component';
import { RoutingModule } from './routing/routing.module';
import { SidenavListComponent } from './components/sidenav-list/sidenav-list.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ShopComponent } from './pages/shop/shop.component';
import { ProductsComponent } from './pages/products/products.component';
import { ProductDetailsComponent } from './pages/product-details/product-details.component';
import { CartComponent } from './pages/cart/cart.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { AdminComponent } from './admin/admin/admin.component';
import { AlertComponent } from './components/alert/alert.component';




@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SliderComponent,
    CollectionsComponent,
    LatestComponent,
    HeaderMenuComponent,
    LayoutComponent,
    HomeComponent,
    SidenavListComponent,
    LoginComponent,
    RegistrationComponent,
    ShopComponent,
    ProductsComponent,
    ProductDetailsComponent,
    CartComponent,
    CheckoutComponent,
    AdminComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    RoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
