package com.ecommerce.servico;

import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.ItemEstoque;
import com.ecommerce.dominio.Vestimenta;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.negocio.vestimenta.ValidaValorDeVenda;
import com.ecommerce.repositorio.VestimentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VestimentaServico {

    private Map<String, List<IStrategy>> regrasNegocio = new HashMap<>();
    private ValidaValorDeVenda validaValorDeVenda = new ValidaValorDeVenda();
    private StringBuilder sb = new StringBuilder();

    @Autowired
    VestimentaRepositorio vestimentaRepositorio;

    @Autowired
    ItemEstoqueServico itemEstoqueServico;

    public VestimentaServico() {
        regrasNegocio.put("salvar", Arrays.asList(
                validaValorDeVenda
            )
        );
        regrasNegocio.put("alterar", Arrays.asList(
                // regras de negocio aqui
                )
        );
    }

    private void executarRegras(EntidadeDominio entidade, List<IStrategy> rnsEntidade) {
        for (IStrategy rn : rnsEntidade) {
            String msg = rn.processar(entidade);
            if (msg != null) {
                sb.append(msg);
            }
        }
    }

    public String salvar(Vestimenta vestimenta) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(vestimenta, rns);
        if (sb.length() == 0) {
            Vestimenta vestimentaBd = vestimentaRepositorio.saveAndFlush(vestimenta);
            ItemEstoque itemEstoque = new ItemEstoque();
            itemEstoque.setVestimenta(vestimentaBd);
            itemEstoque.setQuantidade(0);
            itemEstoque.setDataUltimaEntrada(new Date());
            itemEstoqueServico.salvar(itemEstoque);
        } else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public String editar(Vestimenta vestimenta, Long id) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("alterar");
        executarRegras(vestimenta, rns);
        if(sb.length() == 0) {
            Vestimenta vestimentaBd = buscarPorId(id);
            vestimenta.setId(id);
            vestimentaRepositorio.save(vestimenta);
        }
        else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public void excluir(long id) {
        vestimentaRepositorio.delete(id);
    }

    public List<Vestimenta> consultar() {
        return vestimentaRepositorio.findAll();
    }

    public Vestimenta buscarPorId(Long id) {
        return vestimentaRepositorio.findOne(id);
    }

    public List<Vestimenta> pesquisarVestimenta(String descricao) {
        return vestimentaRepositorio.pesquisarVestimenta(descricao);
    }

    public List<Integer> quantidadeMaisVendidos(Long idVestimenta, Date dataInicial, Date dataFinal) {
        List<Integer> quantidadeMaisVendidos = new ArrayList();
        Date dataAIterar = new Date();
        dataAIterar.setTime(dataInicial.getTime());
        if(dataInicial.getMonth() == dataFinal.getMonth() && dataInicial.getYear() == dataFinal.getYear()) {
            Calendar dataInicialCalendar = Calendar.getInstance();
            dataInicialCalendar.setTime(dataInicial);

            Calendar dataFinalCalendar = Calendar.getInstance();
            dataFinalCalendar.setTime(dataFinal);

            Calendar dataAIterarCalendar = Calendar.getInstance();
            dataAIterarCalendar.setTime(dataAIterar);
            dataAIterarCalendar.add(Calendar.DATE, 2);
            dataInicialCalendar.add(Calendar.DATE, 1);
            for(Calendar dataInicialCalendarFor = dataInicialCalendar; dataInicialCalendarFor.before(dataFinalCalendar); dataInicialCalendarFor.add(Calendar.DATE, 1)) {
                if (quantidadeVendidaPorPeriodo(idVestimenta, dataInicialCalendarFor.getTime(), dataAIterarCalendar.getTime()) != null) {
                    quantidadeMaisVendidos.add(quantidadeVendidaPorPeriodo(idVestimenta, dataInicialCalendarFor.getTime(), dataAIterarCalendar.getTime()));
                } else {
                    quantidadeMaisVendidos.add(0);
                }
                dataAIterarCalendar.add(Calendar.DATE, 1);
            }

        } else {

            dataAIterar.setMonth(dataAIterar.getMonth() + 1);
            for (Date dataInicialFor = dataInicial; dataInicialFor.getTime() < dataFinal.getTime(); dataInicialFor.setMonth(dataInicial.getMonth() + 1)) {
                if (quantidadeVendidaPorPeriodo(idVestimenta, dataInicialFor, dataAIterar) != null) {
                    quantidadeMaisVendidos.add(quantidadeVendidaPorPeriodo(idVestimenta, dataInicialFor, dataAIterar));
                } else {
                    quantidadeMaisVendidos.add(0);
                }
                dataAIterar.setMonth(dataAIterar.getMonth() + 1);
            }


        }
        return quantidadeMaisVendidos;
    }

    public Integer quantidadeVendidaPorPeriodo(Long idVestimenta, Date dataInicial, Date dataFinal) {
        return vestimentaRepositorio.quantidadeVendidaPorPeriodo(idVestimenta, dataInicial, dataFinal);
    }

    public List<Vestimenta> produtosMaisVendidos(Date dataInicial, Date dataFinal) {
        return vestimentaRepositorio.produtosMaisVendidosPorPeriodo(dataInicial, dataFinal);
    }

    public List<Double> quantidadeProdutosMaisFaturados(Date dataInicial, Date dataFinal) {
        return null;
    }

    public List<Vestimenta> produtosMaisFaturados(Date dataInicial, Date dataFinal) {
        return vestimentaRepositorio.produtosMaisFaturadosPorPeriodo(dataInicial, dataFinal);
    }

    public Double quantidadeFaturadaPorPeriodo(Long idVestimenta, Date dataInicial, Date dataFinal) {
        return vestimentaRepositorio.quantidadeFaturadaPorPeriodo(idVestimenta, dataInicial, dataFinal);
    }

}
