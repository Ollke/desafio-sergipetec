package com.desafio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.desafio.entity.Usuario;
import com.desafio.filter.UsuarioFilter;
import com.desafio.repository.usuario.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    private static String htmlConsulta = "usuario/consulta";
    private static String htmldetalhamento = "usuario/detalhamento";
    private static String htmlCadastro = "usuario/cadastro";

    @GetMapping("")
    public ModelAndView exibir(Model model) {
        ModelAndView mv = new ModelAndView(htmlConsulta);

        List<Usuario> consulta = repository.consultar(new UsuarioFilter());
        mv.addObject("usuarios", consulta);

        return mv;
    }

    @PostMapping(value = "/localizar")
    public ModelAndView localizar(@RequestParam MultiValueMap<String, String> formulario) {
        ModelAndView mv = new ModelAndView(htmlConsulta);
        UsuarioFilter filtro = new UsuarioFilter();
        
        if(formulario.containsKey("cdUsuario") && !(formulario.getFirst("cdUsuario")).isEmpty()){
            filtro.setCdUsuario(Integer.parseInt(formulario.getFirst("cdUsuario")));
            mv.addObject("cdUsuario", formulario.getFirst("cdUsuario"));
        }
        
        if(formulario.containsKey("nmUsuario") && !(formulario.getFirst("nmUsuario")).isEmpty()){
            filtro.setNmUsuario(formulario.getFirst("nmUsuario"));
            mv.addObject("nmUsuario", formulario.getFirst("nmUsuario"));
        }
        
        if(formulario.containsKey("email") && !(formulario.getFirst("email")).isEmpty()){
            filtro.setEmail(formulario.getFirst("email"));
            mv.addObject("email", formulario.getFirst("email"));
        }
        
        if(formulario.containsKey("nmAtributoOrdenacao") && !(formulario.getFirst("nmAtributoOrdenacao")).isEmpty()){
            filtro.setNmAtributoOrdenacao(formulario.getFirst("nmAtributoOrdenacao"));
            mv.addObject("nmAtributoOrdenacao", formulario.getFirst("nmAtributoOrdenacao"));
        }
        
        if(formulario.containsKey("tpOrdenacao") && !(formulario.getFirst("tpOrdenacao")).isEmpty()){
            filtro.setTpOrdenacao(formulario.getFirst("tpOrdenacao"));
            mv.addObject("tpOrdenacao", formulario.getFirst("tpOrdenacao"));
        }

        List<Usuario> consulta = repository.consultar(filtro);
        mv.addObject("usuarios", consulta);
        
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
        String nmUsuario = null;
        String email = null;
        
        if(formulario.containsKey("nmUsuario") && !(formulario.getFirst("nmUsuario")).isEmpty()){
            nmUsuario = formulario.getFirst("nmUsuario");
        }
        
        if(formulario.containsKey("email") && !(formulario.getFirst("email")).isEmpty()){
            email = formulario.getFirst("email");
        }

        Usuario usuario = new Usuario();
        usuario.setNmUsuario(nmUsuario);
        usuario.setEmail(email);

        repository.incluir(usuario);

        return "redirect:/usuario";
    }

    @GetMapping("/alterar")
    public ModelAndView exibirAlteracao(@RequestParam("cdUsuario") Integer cdUsuario) {
        ModelAndView mv = new ModelAndView(htmlCadastro);
        mv.addObject("alteracao", true);
        
        List<Usuario> consulta = repository.consultar(new UsuarioFilter(cdUsuario));
        if(consulta.isEmpty()){
            throw new RuntimeException("Usuário não encontrado!");
        }

        mv.addObject("usuario", consulta.get(0));

        return mv;
    }

    @PostMapping(value = "/alterar")
    public String processarAlteracao(@RequestParam MultiValueMap<String, String> formulario) {
        Integer cdUsuario = null;
        String nmUsuario = null;
        String email = null;
        
        if(formulario.containsKey("cdUsuario") && !(formulario.getFirst("cdUsuario")).isEmpty()){
            cdUsuario = Integer.parseInt(formulario.getFirst("cdUsuario"));
        }
        
        if(formulario.containsKey("nmUsuario") && !(formulario.getFirst("nmUsuario")).isEmpty()){
            nmUsuario = formulario.getFirst("nmUsuario");
        }
        
        if(formulario.containsKey("email") && !(formulario.getFirst("email")).isEmpty()){
            email = formulario.getFirst("email");
        }

        List<Usuario> consulta = repository.consultar(new UsuarioFilter(cdUsuario));
        if(!consulta.isEmpty()){
            Usuario usuario = consulta.get(0);

            if(nmUsuario != null)
                usuario.setNmUsuario(nmUsuario);

            if(email != null)
                usuario.setEmail(email);

            repository.alterar(usuario);
        }
        
        return "redirect:/usuario";
    }

    @GetMapping("/detalhar")
    public ModelAndView exibirDetalhamento(@RequestParam("cdUsuario") Integer cdUsuario) {
        ModelAndView mv = new ModelAndView(htmldetalhamento);
        
        List<Usuario> consulta = repository.consultar(new UsuarioFilter(cdUsuario));
        if(consulta.isEmpty()){
            throw new RuntimeException("Usuário não encontrado!");
        }
        
        mv.addObject("usuario", consulta.get(0));
        mv.addObject("exclusao", false);
        return mv;
    }

    @GetMapping("/excluir")
    public ModelAndView exibirExclusao(@RequestParam("cdUsuario") Integer cdUsuario) {
        ModelAndView mv = new ModelAndView(htmldetalhamento);
        
        List<Usuario> consulta = repository.consultar(new UsuarioFilter(cdUsuario));
        if(consulta.isEmpty()){
            throw new RuntimeException("Usuário não encontrado!");
        }
        
        mv.addObject("usuario", consulta.get(0));
        mv.addObject("exclusao", true);
        return mv;
    }

    @PostMapping("/excluir")
    public String processarExclusao(@RequestParam("cdUsuario") Integer cdUsuario) {
        List<Usuario> consulta = repository.consultar(new UsuarioFilter(cdUsuario));
        if(!consulta.isEmpty()){
            if(consultarPedidos(cdUsuario).isEmpty())
                repository.excluir(cdUsuario);
            else
                throw new RuntimeException("Não é possível excluir um usuário com pedidos cadastrados !");
        }

        return "redirect:/usuario";
    }


    private ArrayList consultarPedidos(Integer cdUsuario){
        ArrayList pedidos = new ArrayList<>();

        return pedidos;
    }
}
