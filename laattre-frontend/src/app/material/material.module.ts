import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule, MatSidenavModule, MatToolbarModule, MatIconModule, MatButtonModule, MatListModule, MatMenuModule } from '@angular/material';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatTabsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule, 
    MatButtonModule,
    MatListModule ,
    MatMenuModule  
  ],
  exports: [
    MatTabsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule, 
    MatButtonModule,
    MatListModule,
    MatMenuModule   
  ]
})
export class MaterialModule { }
