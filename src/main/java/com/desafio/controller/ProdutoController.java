package com.desafio.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.desafio.entity.Pedido;
import com.desafio.entity.Produto;
import com.desafio.entity.ProdutoPK;
import com.desafio.filter.PedidoFilter;
import com.desafio.filter.ProdutoFilter;
import com.desafio.repository.pedido.PedidoRepository;
import com.desafio.repository.produto.ProdutoRepository;
import com.desafio.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoService service;

    private static String htmlConsulta = "produto/consulta";
    private static String htmldetalhamento = "produto/detalhamento";
    private static String htmlCadastro = "produto/cadastro";

    @GetMapping("")
    public ModelAndView exibir(@RequestParam(value = "auxiliar", required = false) String auxiliar) {
        ModelAndView mv = new ModelAndView(htmlConsulta);

        if(auxiliar != null)
            mv.addObject("auxiliar", auxiliar);

        List<Produto> consulta = repository.consultar(new ProdutoFilter());
        mv.addObject("produtos", consulta);
        
        return mv;
    }

    @PostMapping(value = "/localizar")
    public ModelAndView localizar(@RequestParam MultiValueMap<String, String> formulario) {
        ModelAndView mv = new ModelAndView(htmlConsulta);
        ProdutoFilter filtro = new ProdutoFilter();
        
        if(formulario.containsKey("cdProduto") && !(formulario.getFirst("cdProduto")).isEmpty()){
            filtro.setCdProduto(Long.parseLong(formulario.getFirst("cdProduto")));
            mv.addObject("cdProduto", formulario.getFirst("cdProduto"));
        }
        
        if(formulario.containsKey("nmProduto") && !(formulario.getFirst("nmProduto")).isEmpty()){
            filtro.setNmProduto(formulario.getFirst("nmProduto"));
            mv.addObject("nmProduto", formulario.getFirst("nmProduto"));
        }
        
        if(formulario.containsKey("dsProduto") && !(formulario.getFirst("dsProduto")).isEmpty()){
            filtro.setDsProduto(formulario.getFirst("dsProduto"));
            mv.addObject("dsProduto", formulario.getFirst("dsProduto"));
        }
        
        if(formulario.containsKey("nmAtributoOrdenacao") && !(formulario.getFirst("nmAtributoOrdenacao")).isEmpty()){
            filtro.setNmAtributoOrdenacao(formulario.getFirst("nmAtributoOrdenacao"));
            mv.addObject("nmAtributoOrdenacao", formulario.getFirst("nmAtributoOrdenacao"));
        }
        
        if(formulario.containsKey("tpOrdenacao") && !(formulario.getFirst("tpOrdenacao")).isEmpty()){
            filtro.setTpOrdenacao(formulario.getFirst("tpOrdenacao"));
            mv.addObject("tpOrdenacao", formulario.getFirst("tpOrdenacao"));
        }      
        
        if(formulario.containsKey("auxiliar") && !(formulario.getFirst("auxiliar")).isEmpty()){
            mv.addObject("auxiliar", formulario.getFirst("auxiliar"));
        }

        List<Produto> consulta = repository.consultar(filtro);
        mv.addObject("produtos", consulta);
        
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView exibirCadastro(Model model) {
        ModelAndView mv = new ModelAndView(htmlCadastro);
        mv.addObject("alteracao", false);
        return mv;
    }

    @PostMapping(value = "/cadastro")
    public String processarCadastro(@RequestParam MultiValueMap<String, String> formulario) {
        String nmProduto = null;
        String dsProduto = null;
        Long qtProduto = null;
        BigDecimal vlProduto = null;
        
        if(formulario.containsKey("nmProduto") && !(formulario.getFirst("nmProduto")).isEmpty()){
            nmProduto = formulario.getFirst("nmProduto");
        }else{
            throw new RuntimeException("O nome do produto é obrigatorio");
        }
        
        if(formulario.containsKey("dsProduto") && !(formulario.getFirst("dsProduto")).isEmpty()){
            dsProduto = formulario.getFirst("dsProduto");
        }else{
            throw new RuntimeException("A descrição do produto é obrigatorio");
        }
        
        if(formulario.containsKey("qtProduto") && !(formulario.getFirst("qtProduto")).isEmpty()){
            qtProduto = Long.parseLong(formulario.getFirst("qtProduto"));
        }else{
            throw new RuntimeException("A quantidade do produto é obrigatorio");
        }
        
        if(formulario.containsKey("vlProduto") && !(formulario.getFirst("vlProduto")).isEmpty()){
            vlProduto = new BigDecimal(formulario.getFirst("vlProduto"));
        }else{
            throw new RuntimeException("O valor do produto é obrigatorio");
        }

        Produto produto = new Produto();
        produto.setNmProduto(nmProduto);
        produto.setDsProduto(dsProduto);
        produto.setQtProduto(qtProduto);
        produto.setVlProduto(vlProduto);

        service.incluir(produto);

        return "redirect:/produto";
    }

    @GetMapping("/alterar")
    public ModelAndView exibirAlteracao(@RequestParam("cdProduto") Long cdProduto) {
        ModelAndView mv = new ModelAndView(htmlCadastro);
        mv.addObject("alteracao", true);
        
        List<Produto> consulta = repository.consultar(new ProdutoFilter(cdProduto));
        if(consulta.isEmpty()){
            throw new RuntimeException("Produto não encontrado!");
        }

        mv.addObject("produto", consulta.get(0));

        return mv;
    }

    @PostMapping(value = "/alterar")
    public String processarAlteracao(@RequestParam MultiValueMap<String, String> formulario) {
        Long cdProduto = null;
        String nmProduto = null;
        String dsProduto = null;
        Long qtProduto = null;
        BigDecimal vlProduto = null;
        
        if(formulario.containsKey("cdProduto") && !(formulario.getFirst("cdProduto")).isEmpty()){
            cdProduto = Long.parseLong(formulario.getFirst("cdProduto"));
        }else{
            throw new RuntimeException("O Código do produto é obrigatorio");
        }        
        
        if(formulario.containsKey("nmProduto") && !(formulario.getFirst("nmProduto")).isEmpty()){
            nmProduto = formulario.getFirst("nmProduto");
        }
        
        if(formulario.containsKey("dsProduto") && !(formulario.getFirst("dsProduto")).isEmpty()){
            dsProduto = formulario.getFirst("dsProduto");
        }
        
        if(formulario.containsKey("qtProduto") && !(formulario.getFirst("qtProduto")).isEmpty()){
            qtProduto = Long.parseLong(formulario.getFirst("qtProduto"));
        }
        
        if(formulario.containsKey("vlProduto") && !(formulario.getFirst("vlProduto")).isEmpty()){
            vlProduto = new BigDecimal(formulario.getFirst("vlProduto"));
        }

        List<Produto> consulta = repository.consultar(new ProdutoFilter(cdProduto));
        if(!consulta.isEmpty()){
            Produto produtoBanco = consulta.get(0);
            Produto produto = new Produto();

            produto.setId(new ProdutoPK());
            produto.getId().setCdProduto(cdProduto);

            if(nmProduto != null)
                produto.setNmProduto(nmProduto);
            else
                produto.setNmProduto(produtoBanco.getNmProduto());

            if(dsProduto != null)
                produto.setDsProduto(dsProduto);
            else
                produto.setDsProduto(produtoBanco.getDsProduto());

            if(qtProduto != null)
                produto.setQtProduto(qtProduto);
            else
                produto.setQtProduto(produtoBanco.getQtProduto());

            if(vlProduto != null)
                produto.setVlProduto(vlProduto);
            else
                produto.setVlProduto(produtoBanco.getVlProduto());

            service.alterar(produto);
        }
        
        return "redirect:/produto";
    }

    @GetMapping("/detalhar")
    public ModelAndView exibirDetalhamento(@RequestParam("cdProduto") Long cdProduto) {
        ModelAndView mv = new ModelAndView(htmldetalhamento);
        
        List<Produto> consulta = repository.consultar(new ProdutoFilter(cdProduto));
        if(consulta.isEmpty()){
            throw new RuntimeException("Usuário não encontrado!");
        }
        
        mv.addObject("pedidos", consultarPedidos(cdProduto));
        
        mv.addObject("produto", consulta.get(0));
        mv.addObject("exclusao", false);
        return mv;
    }

    @GetMapping("/excluir")
    public ModelAndView exibirExclusao(@RequestParam("cdProduto") Long cdProduto) {
        ModelAndView mv = new ModelAndView(htmldetalhamento);
        
        List<Produto> consulta = repository.consultar(new ProdutoFilter(cdProduto));
        if(consulta.isEmpty()){
            throw new RuntimeException("Usuário não encontrado!");
        }
        
        mv.addObject("pedidos", consultarPedidos(cdProduto));

        mv.addObject("produto", consulta.get(0));
        mv.addObject("exclusao", true);
        return mv;
    }

    @PostMapping("/excluir")
    public String processarExclusao(@RequestParam("cdProduto") Long cdProduto) {
        List<Produto> consulta = repository.consultar(new ProdutoFilter(cdProduto));
        if(!consulta.isEmpty()){
            if(consultarPedidos(cdProduto).isEmpty())
                repository.excluir(cdProduto);
            else
                throw new RuntimeException("Não é possível excluir um usuário com pedidos cadastrados !");
        }

        return "redirect:/produto";
    }


    private List<Pedido> consultarPedidos(Long cdProduto){
        PedidoFilter filtro = new PedidoFilter();
        filtro.setCdProduto(cdProduto);

        List<Pedido> consulta = pedidoRepository.consultar(filtro);

        return consulta;
    }
}
