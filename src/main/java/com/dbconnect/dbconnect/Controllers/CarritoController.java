package com.dbconnect.dbconnect.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbconnect.dbconnect.Models.DAO.IClienteDao;
import com.dbconnect.dbconnect.Models.DAO.IProductoDao;


@Controller
public class CarritoController {

    @Autowired
    private IClienteDao IClienteDao;
    @Autowired
    private IProductoDao IProductoDao;

    @GetMapping("/carrito/listar")
    public String ListarCliente(Model model) {
        model.addAttribute("title", "Carrito de compras");
        model.addAttribute("cliente", IClienteDao.findAll());
        model.addAttribute("producto", IProductoDao.findAll());

        return "/carrito/listar";
    }
    

    
    
}
