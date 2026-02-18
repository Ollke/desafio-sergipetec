package com.desafio.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;

@RestControllerAdvice
@NoArgsConstructor
public class MyErrorController{


    @ExceptionHandler(Exception.class)
    @RequestMapping("/error")
    public ModelAndView exibir(Exception exception) {
        ModelAndView mv = new ModelAndView("error");
        String msg = "";

        try {
            msg = exception.getMessage();

            if (msg == null && exception.getCause() != null) {
                msg = exception.getCause().getMessage();
            }
            
            if (msg == null) {
                msg = "Ocorreu um erro inesperado";
            }

        } catch (Exception e) {
            msg = "Ocorreu um erro inesperado";
        }
        
        mv.addObject("msg", msg);
        
        return mv;
    }
}
