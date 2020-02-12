import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PickedComponent } from './picked.component';

describe('PickedComponent', () => {
  let component: PickedComponent;
  let fixture: ComponentFixture<PickedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PickedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PickedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
