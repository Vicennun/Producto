package com.ECOMARKET.Producto.Controller;

import com.ECOMARKET.Producto.Model.Producto;
import com.ECOMARKET.Producto.Service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos  = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
            //alternativa 2 -> return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(productos);
        //alternativa 2 -> return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        Producto productoNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    //    return new ResponseEntity<>(productoNuevo, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String nombre) {
        List<Producto> productos = productoService.findByNombre(nombre);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.findByMarca(marca);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.findByCategoria(categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/precio/menor-que/{precio}")
    public ResponseEntity<List<Producto>> buscarPorPrecioMenor(@PathVariable Double precio) {
        List<Producto> productos = productoService.findByPrecioMenorQue(precio);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/precio/mayor-que/{precio}")
    public ResponseEntity<List<Producto>> buscarPorPrecioMayor(@PathVariable Double precio) {
        List<Producto> productos = productoService.findByPrecioMayorQue(precio);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock/menor-que/{stock}")
    public ResponseEntity<List<Producto>> buscarPorStockMenor(@PathVariable Integer stock) {
        List<Producto> productos = productoService.findByStockMenorQue(stock);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock/mayor-que/{stock}")
    public ResponseEntity<List<Producto>> buscarPorStockMayor(@PathVariable Integer stock) {
        List<Producto> productos = productoService.findByStockMayorQue(stock);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto prod = productoService.findById(id);
            prod.setId(id);
            prod.setNombre(producto.getNombre());
            prod.setMarca(producto.getMarca());
            prod.setDescripcion(producto.getDescripcion());
            prod.setCategoria(producto.getCategoria());
            prod.setPrecio(producto.getPrecio());
            prod.setStock(producto.getStock());
            prod.setImagenUrl(producto.getImagenUrl());

            productoService.save(prod);
            return ResponseEntity.ok(producto);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }
}