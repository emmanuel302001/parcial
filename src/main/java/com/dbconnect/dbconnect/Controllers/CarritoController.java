package com.dbconnect.dbconnect.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbconnect.dbconnect.Models.DAO.ICarritoDao;
import com.dbconnect.dbconnect.Models.DAO.IClienteDao;
import com.dbconnect.dbconnect.Models.DAO.IProductoDao;
import com.dbconnect.dbconnect.Models.Entity.Carrito;
import com.dbconnect.dbconnect.Models.Entity.Cliente;
import com.dbconnect.dbconnect.Models.Entity.Detalles;
import com.dbconnect.dbconnect.Models.Entity.Encabezado;
import com.dbconnect.dbconnect.Models.Entity.Producto;
import com.dbconnect.dbconnect.Models.Entity.factura;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class CarritoController {

    @Autowired
    private IClienteDao IClienteDao;
    @Autowired
    private IProductoDao IProductoDao;
    @Autowired
    private ICarritoDao ICarritoDao;

    public String cliente;

    List<Detalles> detalles = new ArrayList<Detalles>();

    factura factura = new factura();
    Encabezado encabezado = new Encabezado();
    Detalles detalleOrden = new Detalles();
    Cliente clienteOrden = new Cliente();

    @GetMapping("/carrito/listar")
    public String ListarCliente(Model model) {
        model.addAttribute("title", "Carrito de compras");
        model.addAttribute("cliente", IClienteDao.findAll());
        model.addAttribute("producto", IProductoDao.findAll());
        model.addAttribute("carrito", new Carrito());
        factura fact = new factura(new Encabezado(), null);
        model.addAttribute("factura", fact.getDetalle());
        fact.getEncabezado().setSubTotal(0);
        model.addAttribute("encabezado", fact.getEncabezado());
        return "/carrito/listar";
    }

    @GetMapping("/carrito/listar/{id}")
    public String ListarClienteEsp(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Carrito de compras");
        model.addAttribute("cliente", IClienteDao.findAll());
        model.addAttribute("producto", IProductoDao.findAll());
        model.addAttribute("carrito", new Carrito(id));
        factura fact = ICarritoDao.findAll(id);
        model.addAttribute("factura", fact.getDetalle());
        System.out.println("encabezado: " + fact.getEncabezado().getId());
        model.addAttribute("encabezado", fact.getEncabezado());

        return "/carrito/listar";
    }

    @PostMapping("/carrito/form")
    public String saveproduct(@ModelAttribute("carrito") Carrito carrito) {
        // No tiene validación si la cantidad supera el stock
        System.out.println(
                "debug: " + carrito.getIdCliente() + " " + carrito.getIdProducto() + " " + carrito.getCantidad());
        ICarritoDao.add(carrito);
        this.cliente = String.valueOf(carrito.getIdCliente());
        return "redirect:/carrito/listar/" + carrito.getIdCliente() + "";
    }

    @GetMapping("/factura/eliminar/{id}/{idCliente}")
    public String delete(@PathVariable long id, @PathVariable long idCliente) {
        // Long idLong = Long.parseLong(id);
        System.out.println("debug: " + id + " " + idCliente);
        ICarritoDao.delete(id);
        return "redirect:/carrito/listar/" + idCliente + "";
    }

    @GetMapping({ "/descuento" })
    public String validateDiscount(
            @RequestParam(name = "descuento", required = false, defaultValue = "0") int descuento,
            @RequestParam(name = "idEncabezado", required = false, defaultValue = "0") Long idEncabezado,
            @RequestParam(name = "idCliente", required = false, defaultValue = "0") Long idCliente) {
        System.out.println("descuento: " + descuento + " " + idEncabezado);
        ICarritoDao.descuento(descuento, idEncabezado);
        return "redirect:/carrito/listar/" + idCliente + "";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model) {
        Detalles detalleOrden = new Detalles();
        Encabezado encabezado = new Encabezado();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> productoOptional = Optional.ofNullable(IProductoDao.findById(id));
        producto = productoOptional.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setValor(producto.getValorUni() * cantidad);
        encabezado.setTotal(producto.getValorUni() * cantidad);
        detalleOrden.setProducto(producto);

        factura.setEncabezado(encabezado);
        factura.setDetalle(detalles);

        // validar que le producto no se añada 2 veces
        Long idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getValor()).sum();

        encabezado.setTotal(sumaTotal);
        model.addAttribute("cliente", IClienteDao.findAll());
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", factura);

        return "carrito/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Long id, Model model) {

        // lista nueva de prodcutos
        List<Detalles> ordenesNueva = new ArrayList<Detalles>();

        for (Detalles detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNueva.add(detalleOrden);
            }
        }

        // poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getValor()).sum();

        encabezado.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", factura);

        return "carrito/carrito";
    }

    @GetMapping("/detalleorden")
    public String order(Model model) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", factura);
        model.addAttribute("cliente", clienteOrden);

        return "carrito/detalleorden";
    }

    @GetMapping("/getCart")
    public String getCart(Model model) {

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", factura);
        model.addAttribute("cliente", IClienteDao.findAll());

        return "carrito/carrito";
    }

    @PostMapping("/carrito/guardaCarrito")
    public String postMethodName(@ModelAttribute("carrito") Carrito carrito) {
        System.out.println(
                "debug: " + carrito);
        // ICarritoDao.add(carrito);
        this.cliente = String.valueOf(carrito.getIdCliente());
        // return "redirect:/carrito/listar/" + carrito.getIdCliente() + "";
        
        return "carrito/carrito";
    }
    

}
