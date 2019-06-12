import { Injectable } from '@angular/core';
import { Usuario } from '../../dominio';
import { HttpClient } from '@angular/common/http';
import { Url } from '../utilidades/url';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  usuario: any
  urlUsuario: string
  
  constructor(private http: HttpClient, private router: Router) {
    this.urlUsuario = new Url().url.usuario
  }

  /*logar(usuario: Usuario) {
    this.http.post(this.urlUsuario, 
    {
      "operacao":"consultar",
      "email": usuario.email,
      "senha": usuario.senha
    }).subscribe(
      (response) => {
        this.usuario = response
      },
      (error) => {
        console.log(error.error.text)
      }
    )
    
  }

  deslogar() {
    this.usuario = undefined
  }*/

  login(login: string, senha: string) {
    this.logout()
    return this.http.post<any>(this.urlUsuario, { 
      "operacao": "consultar", 
      "email": login,
      "senha": senha 
    }
  ).pipe(map(user => {
            // login successful if there's a jwt token in the response
            if (user) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('currentUser', JSON.stringify(user));
            }
            return user;
        }
      )
    );
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.router.navigate(['/login'])
  }

}
