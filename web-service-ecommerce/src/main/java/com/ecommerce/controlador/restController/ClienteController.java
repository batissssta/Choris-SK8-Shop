/*package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.Cliente;
import com.ecommerce.servico.ClienteServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    ClienteServico clienteServico;

    @PostMapping
    @RequestMapping(value="/salvar" ,  method = RequestMethod.POST)
    public String salvar(@RequestBody Cliente cliente) {
        return clienteServico.salvar(cliente);
    }

    @GetMapping
    public List<Cliente> consultar() {
        return clienteServico.consultar();
    }

    @PostMapping("/editar/{id}")
    public String editar(@RequestBody Cliente cliente, @PathVariable Long id) {
        return clienteServico.editar(cliente, id);
    }

    @GetMapping("excluir/{id}")
    public void excluir(@PathVariable  Long id) {
        clienteServico.excluir(id);
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId (@PathVariable  Long id) {
        return clienteServico.buscarPorId(id);
    }

}
*/