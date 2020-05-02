import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
 	
import { MaterialModule } from './material/material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
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
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { AdminComponent } from './admin/admin/admin.component';
import { AlertComponent } from './components/alert/alert.component';
import { RegistrationSuccessComponent } from './pages/registration-success/registration-success.component';

import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LogoutComponent } from './pages/logout/logout.component';
import { ShoppingCartComponent } from './pages/shopping-cart/shopping-cart.component';
import { AddToCartComponent } from './pages/add-to-cart/add-to-cart.component';
import { EmptyCartComponent } from './pages/empty-cart/empty-cart.component';
import { RegistrationreactiveComponent } from './pages/registrationreactive/registrationreactive.component';

registerLocaleData(localeFr);




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
    CheckoutComponent,
    AdminComponent,
    AlertComponent,
    RegistrationSuccessComponent,
    PageNotFoundComponent,
    LogoutComponent,
    ShoppingCartComponent,
    AddToCartComponent,
    EmptyCartComponent,
    RegistrationreactiveComponent,
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
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: LOCALE_ID,
      useValue: 'fr-FR' // 'de-DE' for Germany, 'fr-FR' for France ... 
    }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
