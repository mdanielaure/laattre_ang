import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule, MatSidenavModule, MatToolbarModule, MatIconModule, MatButtonModule, MatListModule, MatMenuModule, MatTableModule, MatPaginatorModule, MatStepperModule } from '@angular/material';



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
    MatMenuModule ,
    MatTableModule,
    MatPaginatorModule,
    MatStepperModule 
  ],
  exports: [
    MatTabsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule, 
    MatButtonModule,
    MatListModule,
    MatMenuModule ,
    MatTableModule,
    MatPaginatorModule,
    MatStepperModule  
  ]
})
export class MaterialModule { }
