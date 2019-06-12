import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormVestimentaComponent } from './form-vestimenta.component';

describe('FormVestimentaComponent', () => {
  let component: FormVestimentaComponent;
  let fixture: ComponentFixture<FormVestimentaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormVestimentaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormVestimentaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
