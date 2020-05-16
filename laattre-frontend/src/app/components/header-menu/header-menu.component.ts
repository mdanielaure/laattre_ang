import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CategoryService } from 'src/app/services/category.service';
import { map } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-header-menu',
  templateUrl: './header-menu.component.html',
  styleUrls: ['./header-menu.component.css']
})
export class HeaderMenuComponent implements OnInit {

  @Output() public sidenavToggle = new EventEmitter();
  @Output() public destinationToggle = new EventEmitter();

  dropdowns = {
    "destination": false,
   }

   destinationTogg = false
   destinationOver = false;
   destinationOpen = false;

   categories: any;

  constructor(
    private alertService: AlertService,
    private categoryService: CategoryService
  ) { }

  ngOnInit() {
    this.categories = this.getCategories();
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }

  public onToggleDestination = () => {

      if(!this.destinationOpen){
        this.togg("destination");
        this.destinationToggle.emit();
        this.destinationOpen = true;
        this.destinationTogg = true;
      }
    
  }

  public onLeaveTogg = () => {
    setTimeout(() => 
    {
      if(!this.destinationOver){
        this.togg("destination");
        this.destinationToggle.emit();
        this.destinationOpen = false;
        this.destinationTogg = false;
      }
    },
    100);
    
 
}

public onLeaveDestination = () => {

    this.togg("destination");
    this.destinationToggle.emit();
    this.destinationOpen = false;
    this.destinationOver = false;


}

public onOverDestination = () => {
    this.destinationOver = true;
}

  public togg(name: string) {
    this.dropdowns[name] = !this.dropdowns[name];
  }

  getCategories(){
    return this.categoryService.getCategories()
    .pipe(
      map((data)=>{
        return data;
      })
    ).subscribe(
      (data: any) => {
        this.categories = data.categories;
        console.log("categories: " +JSON.stringify(data))
    },
    (error) => {
      this.alertService.error(error);
    });
  }

}
