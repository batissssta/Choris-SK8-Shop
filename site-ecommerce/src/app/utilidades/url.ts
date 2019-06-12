export class Url {
    baseUrlServlet = "http://localhost:8080/generica-servlet/"
    baseUrlRestController = "http://localhost:8080/"
    url = { 
        cliente: this.baseUrlServlet + "cliente",
        vestimenta: this.baseUrlRestController + "vestimentas",
        marca: this.baseUrlRestController + "marcas",
        categoriaVestimenta: this.baseUrlRestController + "categoriasVestimenta",
        endereco: this.baseUrlServlet + "endereco",
        telefone: this.baseUrlServlet + "telefone",
        cartao: this.baseUrlServlet + "cartao",
        usuario: this.baseUrlServlet + "usuario",
        pedido: this.baseUrlRestController + "pedidos",
        itemEstoque: this.baseUrlRestController + "itens-estoque",
        troca: this.baseUrlRestController + "trocas",
        cupomTroca: this.baseUrlServlet + "cupom-troca"
    }
}