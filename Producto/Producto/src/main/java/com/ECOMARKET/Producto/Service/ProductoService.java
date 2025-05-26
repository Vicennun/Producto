package com.ECOMARKET.Producto.Service;

import com.ECOMARKET.Producto.Model.Producto;
import com.ECOMARKET.Producto.Repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository ProductoRepository;

    public List<Producto> findAll() {
        return ProductoRepository.findAll();
    }

    public Producto findById(long id) {
        return ProductoRepository.findById(id).get();
    }

    public Producto save(Producto producto) {
        return ProductoRepository.save(producto);
    }

    public void delete(Long id) {
        ProductoRepository.deleteById(id);
    }

    public List<Producto> findByNombre(String nombre) {
        return ProductoRepository.findByNombre(nombre);
    }

    public List<Producto> findByMarca(String marca) {
        return ProductoRepository.findByMarca(marca);
    }

    public List<Producto> findByCategoria(String categoria) {
        return ProductoRepository.findByCategoria(categoria);
    }

    public List<Producto> findByPrecioMenorQue(Double precio) {
        return ProductoRepository.findByPrecioLessThan(precio);
    }

    public List<Producto> findByPrecioMayorQue(Double precio) {
        return ProductoRepository.findByPrecioGreaterThan(precio);
    }

    public List<Producto> findByStockMenorQue(Integer stock) {
        return ProductoRepository.findByStockLessThan(stock);
    }

    public List<Producto> findByStockMayorQue(Integer stock) {
        return ProductoRepository.findByStockGreaterThan(stock);
    }

}