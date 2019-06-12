import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Vestimenta } from '../../../../dominio';
import { Marca } from '../../../../dominio';
import { CategoriaVestimenta } from '../../../../dominio';
import { Url } from '../../../utilidades/url';
import { DomSanitizer } from '@angular/platform-browser';
import { VestimentaComponent } from '../vestimenta.component';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-form-vestimenta',
  templateUrl: './form-vestimenta.component.html',
  styleUrls: ['./form-vestimenta.component.css']
})
export class FormVestimentaComponent implements OnInit {
  fileName: String
  filePreview: String
  urlVestimenta: string
  urlMarca: string
  urlCategoriaVestimenta: string
  formVestimenta: FormGroup
  vestimentas: Vestimenta[]
  mensagemErro: string
  operacao: string

  marcas: Marca[]
  categorias: CategoriaVestimenta[]

  dtOptions: any
  dtTrigger: Subject<any>
  
  constructor(private http: HttpClient, private fb: FormBuilder, private sanitizer: DomSanitizer) {
    this.dtOptions = {
			responsive: true,
			order: [[ 2, "desc" ]],
			columnDefs: [
				{ searchable: false, targets: [0, 1] },
				{ orderable: false, targets: [0, 1] }
			]
		}
		this.dtTrigger = new Subject()
   }

  ngOnInit() {
    
    this.operacao = 'salvar'
    this.urlVestimenta = new Url().url.vestimenta
    this.urlMarca = new Url().url.marca
    this.urlCategoriaVestimenta = new Url().url.categoriaVestimenta
    this.formVestimenta = this.fb.group({
      id: [''],
      categoria: ['', Validators.required],
      marca: ['', Validators.required],
      cor: ['Selecione...', Validators.required],
      tamanho: ['Selecione...', Validators.required],
      genero: ['Selecione...', Validators.required],
      valor: ['', [Validators.required]],
      imagem: ['']
    })

    this.buscarCategorias()
    this.buscarMarcas()
    this.buscarVestimentas()
  }

  onSubmit(form: FormGroup) {
    this.mensagemErro = undefined
    if(form.valid != true) 
      this.mensagemErro = "Algum dos campos não foi preenchido, preencha todos os campos para concluir o cadastro"
    else {
      // Instanciando objeto de Categoria
      let categoriaVestimenta = new CategoriaVestimenta()
      categoriaVestimenta.nome = form.value.categoria
      // percorrendo lista de categorias para pegar o id
      this.pegarIdCategoria(categoriaVestimenta)
      
      // Instanciando objeto de marca
      let marca = new Marca()
      marca.nome = form.value.marca
      // percorrendo lista de marcas para pegar o id
      this.pegarIdMarca(marca)

      const vestimenta = new Vestimenta
      vestimenta.id = this.operacao == 'salvar' ? null : form.value.id
      vestimenta.marca = marca,
      vestimenta.cor = form.value.cor,
      vestimenta.tamanho = form.value.tamanho,
      vestimenta.genero = form.value.genero,
      vestimenta.categoriaVestimenta = categoriaVestimenta,
      vestimenta.imagem = this.filePreview ? this.filePreview.toString() : null,
      vestimenta.valor = form.value.valor
      
      console.log(vestimenta)
      // request
      this.http.post(this.operacao == 'salvar' ? this.urlVestimenta+"/salvar" : this.urlVestimenta+"/editar/"+vestimenta.id, {
        id: vestimenta.id,
        cor: vestimenta.cor,
        marca: vestimenta.marca.id,
        tamanho: vestimenta.tamanho,
        genero: vestimenta.genero,
        categoriaVestimenta: vestimenta.categoriaVestimenta.id,
        imagem: vestimenta.imagem,
        valor: vestimenta.valor,
        operacao : this.operacao
      }).subscribe(
        (response) => {
          console.log(response)
          this.buscarVestimentas()
          this.formVestimenta.reset()
          if(this.operacao == "salvar") 
            this.mensagemErro = "Vestimenta salva com sucesso"
          else
            this.mensagemErro = "Vestimenta alterada com sucesso"
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text;
        }
      )
    }
    
  }

  buscarMarcas() {
    this.marcas = undefined
    this.http.get<Marca[]>(this.urlMarca)
    .subscribe(
      (response) => {
        this.marcas = response
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  buscarCategorias() {
    this.categorias = undefined
    this.http.get<CategoriaVestimenta[]>(this.urlCategoriaVestimenta)
    .subscribe(
      (response) => {
        this.categorias = response
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  buscarVestimentas() {
    this.vestimentas = undefined
    this.http.get<Vestimenta[]>(this.urlVestimenta).subscribe(
      (response) => {
        this.vestimentas = response
        console.log(this.vestimentas)
      },
      (error) => {
        console.log(error)
      }
    )
  }

  editar(vestimenta: Vestimenta) {
    this.operacao = "edicao"
    this.formVestimenta.get('id').setValue(vestimenta.id)
    this.formVestimenta.get('marca').setValue(vestimenta.marca.nome)
    this.formVestimenta.get('cor').setValue(vestimenta.cor)
    this.formVestimenta.get('tamanho').setValue(vestimenta.tamanho)
    this.formVestimenta.get('genero').setValue(vestimenta.genero)
    this.formVestimenta.get('valor').setValue(vestimenta.valor)
    this.formVestimenta.get('categoria').setValue(vestimenta.categoriaVestimenta.nome)
  }

  excluir(id: number) {
    this.http.get(this.urlVestimenta + "/excluir/" + id).subscribe(
        (response) => {
          console.log(response)
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text;
        }
      )
      this.buscarVestimentas()
  }

  pegarIdCategoria(categoriaVestimenta: CategoriaVestimenta) {
    this.categorias.forEach(function(value, index) {
      if(value.nome == categoriaVestimenta.nome)
        categoriaVestimenta.id = value.id
      //categoriaVestimenta.id = categoriaVestimenta.nome == value.nome ? value.id : undefined
    });
  }

  pegarIdMarca(marca: Marca) {
    this.marcas.forEach(function(value, index) {
      if(value.nome == marca.nome)
        marca.id = value.id
    }); 
  }

  // convert file to base64
  onFileChanged(event) {
    let reader = new FileReader();
    if (event.target.files && event.target.files.length > 0) {
      let file = event.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.fileName = file.name + " " + file.type;
        this.filePreview = 'data:image/png' + ';base64,' + reader.result.toString().split(',')[1];
        console.log("File name = ", this.fileName)
        console.log("File preview = ", this.filePreview)
      };
    }
  }

  // return a base64 image to an image
  sanitize(url: string) {
    //return url;
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  fechaMensagemErro() {
    this.mensagemErro = null
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

}
