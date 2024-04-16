package com.dbconnect.dbconnect.Controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dbconnect.dbconnect.Models.DAO.IProductoDao;
import com.dbconnect.dbconnect.Models.Entity.Producto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoDao IProductoDao;

    @GetMapping({ "/home", "/" })
    public String home(Model model) {
        model.addAttribute("productos", IProductoDao.findAll());
        return "/home";
    }

}
