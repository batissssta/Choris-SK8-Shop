import { Component, OnInit, TemplateRef } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { UsuarioService } from '../../servicos/usuario.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Usuario } from '../../../dominio';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  formPesquisarVestimentas: FormGroup
  usuarioLogado: Usuario
  modalRef: BsModalRef

  constructor(private route: Router, private usuarioService: UsuarioService, private fb: FormBuilder, private modalService: BsModalService) { }
  
  ngOnInit() {
    this.formPesquisarVestimentas = this.fb.group({
      descricao: ['']
    }); 
    this.usuarioLogado = JSON.parse(localStorage.getItem('currentUser'));
  }

  pesquisarVestimentas(form: FormGroup) {
    let descricao = form.value.descricao
    
    let navigationExtras: NavigationExtras = {
      queryParams: {
        "filtro": descricao
      }
    };
    this.route.navigate(["/resultados-busca"], navigationExtras);
  }

  logout() {
    this.usuarioService.logout()
    this.modalRef.hide()
    this.ngOnInit()
  }
  
  voltarHome() {
    this.route.navigate(["/"])
  }
  
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template); 
  }

}
