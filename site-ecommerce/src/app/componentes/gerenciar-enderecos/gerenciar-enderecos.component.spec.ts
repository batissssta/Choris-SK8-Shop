import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarEnderecosComponent } from './gerenciar-enderecos.component';

describe('GerenciarEnderecosComponent', () => {
  let component: GerenciarEnderecosComponent;
  let fixture: ComponentFixture<GerenciarEnderecosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GerenciarEnderecosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciarEnderecosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
