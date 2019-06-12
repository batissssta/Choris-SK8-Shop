package com.ecommerce.repositorio;

import com.ecommerce.dominio.ItemPedido;
import com.ecommerce.dominio.Vestimenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VestimentaRepositorio extends JpaRepository<Vestimenta, Long> {

    @Query(
            "SELECT vestimenta " +
                    "FROM Vestimenta vestimenta " +
                    "WHERE vestimenta.categoriaVestimenta.nome LIKE %?1 " +
                    "OR vestimenta.marca.nome LIKE %?1 " +
                    "OR vestimenta.cor LIKE %?1 ")
    List<Vestimenta> pesquisarVestimenta(String descricao);


    @Query(
            "SELECT vestimenta " +
                    "from ItemPedido itemPedido " +
                    "where itemPedido.pedido.data between ?1 and ?2 " +
                    "group by itemPedido.vestimenta " +
                    "order by SUM(itemPedido.quantidade) DESC "
    )
    List<Vestimenta> produtosMaisVendidosPorPeriodo(Date dataInicial, Date dataFinal);


    @Query(
            "SELECT SUM(itemPedido.quantidade) " +
                "from ItemPedido itemPedido " +
                "where itemPedido.pedido.data between ?2 and ?3 " +
                "AND itemPedido.vestimenta.id = ?1 " +
                "group by itemPedido.vestimenta " +
                "order by SUM(itemPedido.quantidade) DESC "
    )
    Integer quantidadeVendidaPorPeriodo(Long idVestimenta, Date dataInicial, Date dataFinal);

    @Query(
            "SELECT vestimenta " +
                    "from ItemPedido itemPedido " +
                    "where itemPedido.pedido.data between  ?1 and ?2 " +
                    "group by itemPedido.vestimenta " +
                    "order by SUM(itemPedido.vestimenta.valor) DESC "
    )
    List<Vestimenta> produtosMaisFaturadosPorPeriodo(Date dataInicial, Date dataFinal);

    @Query(
            "SELECT sum(itemPedido.vestimenta.valor * itemPedido.quantidade) " +
                    "from ItemPedido itemPedido " +
                    "where itemPedido.pedido.data between ?2 and ?3 " +
                    "and itemPedido.vestimenta.id = ?1 " +
                    "group by itemPedido.vestimenta " +
                    "order by sum(itemPedido.vestimenta.valor * itemPedido.quantidade) DESC "
    )
    Double quantidadeFaturadaPorPeriodo(Long idVestimenta, Date dataInicial, Date dataFinal);

}
