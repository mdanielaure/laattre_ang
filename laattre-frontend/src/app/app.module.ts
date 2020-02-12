import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SliderComponent } from './components/slider/slider.component';
import { PickedComponent } from './components/picked/picked.component';
import { DealsComponent } from './components/deals/deals.component';
import { CollectionsComponent } from './components/collections/collections.component';
import { HotdealComponent } from './components/hotdeal/hotdeal.component';
import { LatestComponent } from './components/latest/latest.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SliderComponent,
    PickedComponent,
    DealsComponent,
    CollectionsComponent,
    HotdealComponent,
    LatestComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
