package com.ecommerce.controlador;

import com.ecommerce.controlador.command.*;
import com.ecommerce.controlador.viewHelper.*;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dto.ClienteDTO;
import com.ecommerce.dto.EntidadeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@WebServlet(urlPatterns = "/generica-servlet/*")
public class Servlet extends HttpServlet {
    private Map<String, ICommand> commands;
    private Map<String, IViewHelper> vhs;

    public Servlet() {
        commands = new HashMap<String, ICommand>();
        commands.put("salvar", new SalvarCommand());
        commands.put("consultar", new ConsultarCommand());
        commands.put("alterar", new AlterarCommand());
        commands.put("excluir", new ExcluirCommand());


        vhs = new HashMap<String, IViewHelper>();
        vhs.put("/generica-servlet/cliente", new ClienteViewHelper());
        vhs.put("/generica-servlet/usuario", new UsuarioViewHelper());
        vhs.put("/generica-servlet/endereco", new EnderecoViewHelper());
        vhs.put("/generica-servlet/cartao", new CartaoViewHelper());
        vhs.put("/generica-servlet/cupom-troca", new CupomTrocaViewHelper());

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // convertendo a requisição pra objeto java para pegar a operação
        String json = request.getReader().readLine();
        Gson gson = new Gson();
        EntidadeDTO resultadoDTO = gson.fromJson(json, EntidadeDTO.class);
        String operacao = resultadoDTO.getOperacao();

        Resultado resultado = null;
        IViewHelper vh = vhs.get(request.getRequestURI());
        EntidadeDominio entidade = vh.getEntidade(json);
        // nesse momento, posso passar o retorno de uma operação do jeito que eu quiser
        // nesse exemplo, estou passando um resultado
        // mas poderia estar passando uma string por exemplo
        // se o set view do meu view helper soubesse retornar uma string ou uma lista pro font end
        resultado = commands.get(operacao).executar(entidade);
        vh.setView(resultado, json, response);

    }
}
