import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteServletComponent } from './cliente-servlet.component';

describe('ClienteServletComponent', () => {
  let component: ClienteServletComponent;
  let fixture: ComponentFixture<ClienteServletComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClienteServletComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteServletComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
