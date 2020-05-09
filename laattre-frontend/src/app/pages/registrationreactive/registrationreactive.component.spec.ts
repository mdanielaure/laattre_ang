import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationreactiveComponent } from './registrationreactive.component';

describe('RegistrationreactiveComponent', () => {
  let component: RegistrationreactiveComponent;
  let fixture: ComponentFixture<RegistrationreactiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationreactiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationreactiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
