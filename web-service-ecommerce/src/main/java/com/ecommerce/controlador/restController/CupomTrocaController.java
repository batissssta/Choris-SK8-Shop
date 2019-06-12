package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.CupomTroca;
import com.ecommerce.servico.CupomTrocaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("cupons-troca")
public class CupomTrocaController {
    @Autowired
    CupomTrocaServico cupomTrocaServico;

    @PostMapping
    @RequestMapping(value="/salvar" ,  method = RequestMethod.POST)
    public String salvar(@RequestBody CupomTroca cupomTroca) {
        return cupomTrocaServico.salvar(cupomTroca);
    }

}
