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
  @Output() public menuToggle = new EventEmitter();
  

  dropdowns = { "menu1": false, "menu2": false, "menu3": false, "menu4": false, "menu5": false, "menu6": false, "menu7": false, "menu8": false };
  menuOpen = { "menu1": false, "menu2": false, "menu3": false, "menu4": false, "menu5": false, "menu6": false, "menu7": false, "menu8": false };
  menuOver = { "menu1": false, "menu2": false, "menu3": false, "menu4": false, "menu5": false, "menu6": false, "menu7": false, "menu8": false };
  menuTogg = { "menu1": false, "menu2": false, "menu3": false, "menu4": false, "menu5": false, "menu6": false, "menu7": false, "menu8": false };

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

  public onToggleMenu = (menuName: string) => {


      if(!this.menuOpen[menuName]){
        this.togg(menuName);
        this.menuToggle.emit();
        this.isMenuOpen(menuName, true);
        this.menuTogg[menuName] = true;
      }
    
  }

  public onLeaveTogg = (menuName: string) => {
    setTimeout(() => 
    {
      if(!this.menuOver[menuName]){
        this.togg(menuName);
        this.menuToggle.emit();
        this.isMenuOpen(menuName, false);
        this.menuTogg[menuName] = false;
      }
    },
    100);
    
 
}

public onLeaveMenu = (menuName: string) => {

    this.togg(menuName);
    this.menuToggle.emit();
    this.isMenuOpen(menuName, false);
    this.isMenuOver(menuName, false);


}

public onOverMenu = (menuName: string) => {
    this.isMenuOver(menuName, true);
}

onClickMenu = (menuName: string) => {
  /*if(!this.menuOpen[menuName]){
    this.togg(menuName);
    this.menuToggle.emit();
    this.menuOpen[menuName] = true;
    this.menuTogg[menuName] = true;
  }
  else if(this.menuOpen[menuName]){
    if(!this.menuOver[menuName]){
      this.togg(menuName);
      this.menuToggle.emit();
      this.isMenuOpen(menuName, false);
      this.menuTogg[menuName] = false;
    }
  }*/
  this.togg(menuName);
  
}

  public togg(name: string) {
    this.dropdowns[name] = !this.dropdowns[name];
  }

  public isMenuOpen(name: string, value: boolean){
    this.menuOpen[name] = value;
  }

  public isMenuOver(name: string, value: boolean){
    this.menuOver[name] = value;
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
