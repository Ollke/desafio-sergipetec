package com.desafio.controller;

import java.math.BigDecimal;
import java.sql.Date;
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
import com.desafio.entity.PedidoDetalhe;
import com.desafio.entity.PedidoDetalhePK;
import com.desafio.filter.PedidoFilter;
import com.desafio.filter.UsuarioFilter;
import com.desafio.repository.pedido.PedidoRepository;
import com.desafio.repository.pedidoDetalhe.PedidoDetalheRepository;
import com.desafio.repository.usuario.UsuarioRepository;
import com.desafio.service.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;
    @Autowired
    private PedidoDetalheRepository detalheRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoService service;

    private static String htmlConsulta = "pedido/consulta";
    private static String htmldetalhamento = "pedido/detalhamento";
    private static String htmlCadastro = "pedido/cadastro";

    @GetMapping("")
    public ModelAndView exibir(@RequestParam(value = "auxiliar", required = false) String auxiliar) {
        ModelAndView mv = new ModelAndView(htmlConsulta);

        if(auxiliar != null)
            mv.addObject("auxiliar", auxiliar);

        List<Pedido> consulta = repository.consultar(new PedidoFilter());
        mv.addObject("pedidos", consulta);
        
        return mv;
    }

    @PostMapping(value = "/localizar")
    public ModelAndView localizar(@RequestParam MultiValueMap<String, String> formulario) {
        ModelAndView mv = new ModelAndView(htmlConsulta);
        PedidoFilter filtro = new PedidoFilter();
        
        if(formulario.containsKey("cdPedido") && !(formulario.getFirst("cdPedido")).isEmpty()){
            filtro.setCdPedido(Long.parseLong(formulario.getFirst("cdPedido")));
            mv.addObject("cdPedido", formulario.getFirst("cdPedido"));
        }
        if(formulario.containsKey("cdProduto") && !(formulario.getFirst("cdProduto")).isEmpty()){
            filtro.setCdProduto(Long.parseLong(formulario.getFirst("cdProduto")));
            mv.addObject("cdProduto", formulario.getFirst("cdProduto"));
        }
        if(formulario.containsKey("cdUsuario") && !(formulario.getFirst("cdUsuario")).isEmpty()){
            filtro.setCdUsuario(Long.parseLong(formulario.getFirst("cdUsuario")));
            mv.addObject("cdUsuario", formulario.getFirst("cdUsuario"));
        }
        
        if(formulario.containsKey("nmAtributoOrdenacao") && !(formulario.getFirst("nmAtributoOrdenacao")).isEmpty()){
            filtro.setNmAtributoOrdenacao(formulario.getFirst("nmAtributoOrdenacao"));
            mv.addObject("nmAtributoOrdenacao", formulario.getFirst("nmAtributoOrdenacao"));
        }
        
        if(formulario.containsKey("tpOrdenacao") && !(formulario.getFirst("tpOrdenacao")).isEmpty()){
            filtro.setTpOrdenacao(formulario.getFirst("tpOrdenacao"));
            mv.addObject("tpOrdenacao", formulario.getFirst("tpOrdenacao"));
        }  

        List<Pedido> consulta = repository.consultar(filtro);
        mv.addObject("pedidos", consulta);
        
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView exibirCadastro(Model model) {
        ModelAndView mv = new ModelAndView(htmlCadastro);
        return mv;
    }

    @PostMapping(value = "/cadastro")
    public String processarCadastro(@RequestParam MultiValueMap<String, String> formulario) {
        Pedido pedido = new Pedido();
        ArrayList<PedidoDetalhe> detalhes = new ArrayList<>();
        
        if(formulario.containsKey("cdUsuario") && !(formulario.getFirst("cdUsuario")).isEmpty()){
            pedido.setCdUsuario(Long.valueOf(formulario.getFirst("cdUsuario")));
        }else{
            throw new RuntimeException("O Cliente é obrigatorio");
        }
        
        if(formulario.containsKey("dtCadastro") && !(formulario.getFirst("dtCadastro")).isEmpty()){
            pedido.setDtCadastro(Date.valueOf(formulario.getFirst("dtCadastro")));
        }else{
            throw new RuntimeException("A Data é obrigatorio");
        }
        
        if(formulario.containsKey("pcDesconto") && !(formulario.getFirst("pcDesconto")).isEmpty()){
            pedido.setPcDesconto(new BigDecimal(formulario.getFirst("pcDesconto")));
        }else{
            pedido.setPcDesconto(BigDecimal.ZERO);
        }
        
        if(formulario.containsKey("produtos") && !(formulario.getFirst("produtos")).isEmpty()){
            for(String produto : formulario.getFirst("produtos").split("\\*")){

                PedidoDetalhe detalhe = new PedidoDetalhe();
                detalhe.setId(new PedidoDetalhePK());

                detalhe.getId().setCdProduto(Long.valueOf(produto));
                detalhe.setQtProduto(Long.valueOf(formulario.getFirst("qtProduto"+produto)));

                detalhes.add(detalhe);
            }
        }else{
            throw new RuntimeException("O Produto é obrigatorio");
        }

        service.incluir(pedido, detalhes);

        return "redirect:/pedido";
    }

    @GetMapping("/detalhar")
    public ModelAndView exibirDetalhamento(@RequestParam("cdPedido") Long cdPedido) {
        ModelAndView mv = new ModelAndView(htmldetalhamento);
        
        List<Pedido> consulta = repository.consultar(new PedidoFilter(cdPedido));
        if(consulta.isEmpty()){
            throw new RuntimeException("Pedido não encontrado!");
        }

        String nmUsuario = usuarioRepository.consultar(new UsuarioFilter(consulta.get(0).getCdUsuario())).get(0).getNmUsuario();
        List<Map<String, Object>> detalhes =  detalheRepository.consultarHash(new PedidoFilter(cdPedido));
        
        mv.addObject("pedido", consulta.get(0));
        mv.addObject("nmUsuario", nmUsuario);
        mv.addObject("detalhes", detalhes);
        mv.addObject("exclusao", false);
        return mv;
    }

    @GetMapping("/excluir")
    public ModelAndView exibirExclusao(@RequestParam("cdPedido") Long cdPedido) {
        ModelAndView mv = new ModelAndView(htmldetalhamento);
        
        List<Pedido> consulta = repository.consultar(new PedidoFilter(cdPedido));
        if(consulta.isEmpty()){
            throw new RuntimeException("Pedido não encontrado!");
        }
        
        String nmUsuario = usuarioRepository.consultar(new UsuarioFilter(consulta.get(0).getCdUsuario())).get(0).getNmUsuario();
        List<Map<String, Object>> detalhes =  detalheRepository.consultarHash(new PedidoFilter(cdPedido));
        
        mv.addObject("pedido", consulta.get(0));
        mv.addObject("nmUsuario", nmUsuario);
        mv.addObject("detalhes", detalhes);
        mv.addObject("exclusao", true);
        return mv;
    }

    @PostMapping("/excluir")
    public String processarExclusao(@RequestParam("cdPedido") Long cdPedido) {
        List<Pedido> consulta = repository.consultar(new PedidoFilter(cdPedido));
        if(!consulta.isEmpty()){
            service.excluir(cdPedido);
        }

        return "redirect:/pedido";
    }
}
