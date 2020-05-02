import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule, MatSidenavModule, MatToolbarModule, MatIconModule, MatButtonModule, MatListModule, MatMenuModule, MatTableModule, MatPaginatorModule } from '@angular/material';



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
    MatPaginatorModule 
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
    MatPaginatorModule  
  ]
})
export class MaterialModule { }
