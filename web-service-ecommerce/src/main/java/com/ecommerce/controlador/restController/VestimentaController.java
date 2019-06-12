package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.Vestimenta;
import com.ecommerce.dto.PeriodoDTO;
import com.ecommerce.servico.VestimentaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("vestimentas")
public class VestimentaController {

    @Autowired
    VestimentaServico vestimentaServico;

    @PostMapping
    @RequestMapping(value="/salvar" ,  method = RequestMethod.POST)
    public String salvar(@RequestBody Vestimenta vestimenta) {
        return vestimentaServico.salvar(vestimenta);
    }

    @GetMapping
    public List<Vestimenta> consultar() {
        return vestimentaServico.consultar();
    }

    @PostMapping("/editar/{id}")
    public String editar(@RequestBody Vestimenta vestimenta, @PathVariable Long id) {
        return vestimentaServico.editar(vestimenta, id);
    }

    @PostMapping("/pesquisarVestimentas/{descricao}")
    public List<Vestimenta> pesquisarVestimenta(@PathVariable String descricao) {
        return vestimentaServico.pesquisarVestimenta(descricao);
    }

    @GetMapping("excluir/{id}")
    public void excluir(@PathVariable  Long id) {
        vestimentaServico.excluir(id);
    }

    @GetMapping("/{id}")
    public Vestimenta buscarPorId (@PathVariable  Long id) {
        return vestimentaServico.buscarPorId(id);
    }

    @PostMapping
    @RequestMapping(value="/quantidade-produtos-mais-vendidos", method = RequestMethod.POST)
    public List<Integer> quantidadeProdutosMaisVendidos(@RequestBody PeriodoDTO periodoDTO) {
        return vestimentaServico.quantidadeMaisVendidos(periodoDTO.getIdVestimenta(), periodoDTO.getDataInicial(), periodoDTO.getDataFinal());
    }

    @PostMapping
    @RequestMapping(value="/produtos-mais-vendidos", method = RequestMethod.POST)
    public List<Vestimenta> produtosMaisVendidos(@RequestBody PeriodoDTO periodoDTO) {
        return vestimentaServico.produtosMaisVendidos(periodoDTO.getDataInicial(), periodoDTO.getDataFinal());
    }

    @PostMapping
    @RequestMapping(value="/quantidade-produtos-mais-faturados", method = RequestMethod.POST)
    public List<Double> quantidadeProdutosMaisFaturados(@RequestBody PeriodoDTO periodoDTO) {
        return vestimentaServico.quantidadeProdutosMaisFaturados(periodoDTO.getDataInicial(), periodoDTO.getDataFinal());
    }

    @PostMapping
    @RequestMapping(value="/produtos-mais-faturados", method = RequestMethod.POST)
    public List<Vestimenta> produtosMaisFaturados(@RequestBody PeriodoDTO periodoDTO) {
        return vestimentaServico.produtosMaisFaturados(periodoDTO.getDataInicial(), periodoDTO.getDataFinal());
    }

}
