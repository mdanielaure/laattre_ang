import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private messageSource = new BehaviorSubject<string>("");
  public currentMessage = this.messageSource.asObservable();

  private dataSource = new BehaviorSubject<any>(null);
  public currentData = this.dataSource.asObservable();

  constructor() { }

  changeMessage(message: string){
    this.messageSource.next(message);
    localStorage.setItem('currentMessage', message);
  }

  changeData(data: any){
    this.dataSource.next(data);
    localStorage.setItem('currentData', data);
  }
}
