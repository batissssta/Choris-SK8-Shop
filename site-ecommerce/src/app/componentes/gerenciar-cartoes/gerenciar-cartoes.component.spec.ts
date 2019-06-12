import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarCartoesComponent } from './gerenciar-cartoes.component';

describe('GerenciarCartoesComponent', () => {
  let component: GerenciarCartoesComponent;
  let fixture: ComponentFixture<GerenciarCartoesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GerenciarCartoesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciarCartoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
