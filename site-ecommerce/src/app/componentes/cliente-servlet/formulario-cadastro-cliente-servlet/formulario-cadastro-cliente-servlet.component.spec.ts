import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioCadastroClienteServletComponent } from './formulario-cadastro-cliente-servlet.component';

describe('FormularioCadastroClienteServletComponent', () => {
  let component: FormularioCadastroClienteServletComponent;
  let fixture: ComponentFixture<FormularioCadastroClienteServletComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormularioCadastroClienteServletComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormularioCadastroClienteServletComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
