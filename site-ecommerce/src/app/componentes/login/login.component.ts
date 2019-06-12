import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../servicos/usuario.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Usuario } from '../../../dominio';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertService } from '../../servicos/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  formLogin: FormGroup
  mensagemErro: string = null

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.formLogin = this.fb.group({
      email: ['', Validators.required],
      senha: ['', Validators.required]
    });
  }

  login(form: FormGroup) {
    this.usuarioService.login(
      form.value.email, 
      form.value.senha
    ).pipe(first())
      .subscribe(
        data => {
            this.router.navigate(['/']);
        },
        error => {
          this.mensagemErro = error.error.text;
        }
      );
  }

  voltarHome() {
    this.router.navigate(['/'])
  }

  fechaMensagemErro() {
    this.mensagemErro = null
  }

}
