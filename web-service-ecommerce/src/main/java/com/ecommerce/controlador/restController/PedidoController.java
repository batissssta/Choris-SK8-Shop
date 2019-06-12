package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.Pedido;
import com.ecommerce.servico.PedidoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("pedidos")
public class
PedidoController {
    @Autowired
    PedidoServico pedidoServico;

    @PostMapping
    @RequestMapping(value="/salvar" ,  method = RequestMethod.POST)
    public String salvar(@RequestBody Pedido pedido) {
        return pedidoServico.salvar(pedido);
    }

    @GetMapping
    public List<Pedido> consultar() {
        return pedidoServico.consultar();
    }

    @PostMapping("/editar/{id}")
    public String editar(@RequestBody Pedido pedido, @PathVariable Long id) {
        return pedidoServico.editar(pedido, id);
    }

    @GetMapping("excluir/{id}")
    public void excluir(@PathVariable  Long id) {
        pedidoServico.excluir(id);
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId (@PathVariable  Long id) {
        return pedidoServico.buscarPorId(id);
    }

    @PostMapping("/buscarPorIdUsuario/{id}")
    public List<Pedido> buscarPorIdUsuario (@PathVariable  Long id) {
        return pedidoServico.buscarPorIdUsuario(id);
    }

}
