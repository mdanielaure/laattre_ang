import { Component, OnInit, Output, EventEmitter } from '@angular/core';

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

  constructor() { }

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
    //retrun this.categoryService.getCategories();
  }

}
