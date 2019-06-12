export class Entidade {
    id: number

    constructor(params?: Partial<Entidade>){
		if (params) {
			this.id = params.id
		}
	}
}

export class Cliente extends Entidade {
    nome: string
    dataNascimento: Date
    genero: string
    cpf: string
    email: string
    senha: string
    confirmarSenha: string
    telefones: Telefone[]
    enderecos: Endereco[]
    cartoes: Cartao[]
    status: string

	constructor(params?: Partial<Cliente>){
		super()
		if (params) {
            super(params)
            this.nome = params.nome
            this.genero = params.genero
            this.cpf = params.cpf
            this.email = params.email
            this.senha = params.senha
            this.status = params.status
            this.confirmarSenha = params.confirmarSenha
            this.telefones = params.telefones
            this.cartoes = params.cartoes
            this.enderecos = params.enderecos
		}
	}
}

export class Vestimenta extends Entidade {
    marca: Marca
    cor: string
    tamanho: string
    genero: string
    categoriaVestimenta: CategoriaVestimenta
    imagem: string
    valor: number
	
	constructor(params?: Partial<Vestimenta>){
		super()
		if (params) {
            super(params)
            this.marca = params.marca
            this.cor = params.cor
            this.tamanho = params.tamanho
            this.genero = params.genero
            this.categoriaVestimenta = params.categoriaVestimenta
            this.imagem = params.imagem
			this.valor = params.valor
		}
	}
}

export class Marca extends Entidade {
    nome: string
    
	constructor(params?: Partial<Marca>){
		super()
		if (params) {
            super(params)
            this.nome = params.nome
    	}
	}
}

export class CategoriaVestimenta extends Entidade {
    nome: string
    
	constructor(params?: Partial<CategoriaVestimenta>){
		super()
		if (params) {
            super(params)
            this.nome = params.nome
    	}
	}
}

export class Telefone extends Entidade {
    tipo: string
    numero: string
    cliente: Cliente
	constructor(params?: Partial<Telefone>){
		super()
		if (params) {
            super(params)
            this.tipo = params.tipo
            this.numero = params.tipo
            this.cliente = params.cliente
    	}
	}
}

export class Endereco extends Entidade {
    logradouro: string
    numero: string
    cep: string
    tipoLogradouro: string
    tipoResidencia: string
    bairro: string
    cidade: string
    estado: string
    pais: string
    tipoEndereco: string
    cliente: Cliente
	constructor(params?: Partial<Endereco>){
		super()
		if (params) {
            super(params)
            this.logradouro = params.logradouro
            this.numero = params.numero
            this.cep = params.cep
            this.tipoLogradouro = params.tipoLogradouro
            this.tipoResidencia = params.tipoResidencia
            this.bairro = params.bairro
            this.cidade = params.cidade
            this.estado = params.estado
            this.pais = params.pais
            this.tipoEndereco = params.tipoEndereco
            this.cliente = params.cliente
    	}
	}
}

export class Cartao extends Entidade {
    codigoSeguranca: string
    numeroCartao: string
    validade: Date
    preferido: boolean
    nomeImpresso: string
    bandeiraCartao: string
    cliente: Cliente
    constructor(params?: Partial<Cartao>){
		super()
		if (params) {
            super(params)
            this.codigoSeguranca = params.codigoSeguranca
            this.numeroCartao = params.numeroCartao
            this.validade = params.validade
            this.preferido = params.preferido
            this.nomeImpresso = params.nomeImpresso
            this.bandeiraCartao = params.bandeiraCartao
            this.cliente = params.cliente
        }
	}
}

export class CupomTroca extends Entidade {
    valor: number
    cliente: Cliente
    constructor(params?: Partial<CupomTroca>){
		super()
		if (params) {
            super(params)
            this.valor  = params.valor
            this.cliente = params.cliente
        }
	}
}


export class Usuario extends Entidade {
    nome: string
    email: string
    senha: string
    permissao: string
    constructor(params?: Partial<Usuario>){
		super()
		if (params) {
            super(params)
            this.nome = params.nome
            this.email = params.email
            this.senha = params.senha
            this.permissao = params.permissao
        }
	}
}


export class ItemPedido extends Entidade {
    quantidade: number
    vestimenta: Vestimenta
    pedido: Pedido
    constructor(params?: Partial<ItemPedido>){
		super()
		if (params) {
            super(params)
            this.quantidade = params.quantidade
            this.vestimenta = params.vestimenta
            this.pedido = params.pedido
        }
	}
}

export class Pedido extends Entidade {
    data: Date
    valor: number
    usuario: Usuario
    endereco: Endereco
    cartao: Cartao
    cupomTroca: CupomTroca
    itens: ItemPedido[]
    statusPedido: StatusPedido
    constructor(params?: Partial<Pedido>){
		super()
		if (params) {
            super(params)
            this.data = params.data
            this.usuario = params.usuario
            this.endereco = params.endereco
            this.cartao = params.cartao
            this.itens = params.itens
            this.statusPedido = params.statusPedido
        }
	}
}

export class StatusPedido extends Entidade {
    descricao: string
    constructor(params?: Partial<StatusPedido>){
		super()
		if (params) {
            super(params)
            this.descricao = params.descricao
        }
	}
}

export class ItemEstoque extends Entidade {
    quantidade: number
    vestimenta: Vestimenta
    constructor(params?: Partial<ItemEstoque>){
		super()
		if (params) {
            super(params)
            this.quantidade = params.quantidade
            this.vestimenta = params.vestimenta
        }
	}
}

