package com.ECOMARKET.Producto.Repository;

import com.ECOMARKET.Producto.Model.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombre(String nombre);
    List<Producto> findByMarca(String marca);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByPrecioLessThan(Double precio);
    List<Producto> findByPrecioGreaterThan(Double precio);
    List<Producto> findByStockLessThan(Integer stock);
    List<Producto> findByStockGreaterThan(Integer stock);
}
