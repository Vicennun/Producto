package com.ECOMARKET.Producto;

import com.ECOMARKET.Producto.Model.Categoria;
import com.ECOMARKET.Producto.Model.Producto;
import com.ECOMARKET.Producto.Repository.CategoriaRepository;
import com.ECOMARKET.Producto.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.util.*;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        productoRepository.deleteAll();
        categoriaRepository.deleteAll();

        Faker faker = new Faker();
        // Crear algunas categorías
        List<Categoria> categorias = Arrays.asList(
                new Categoria(null, "Frutas", "Frutas frescas y orgánicas"),
                new Categoria(null, "Lácteos", "Productos lácteos naturales"),
                new Categoria(null, "Panadería", "Pan y productos de panadería"),
                new Categoria(null, "Aceites", "Aceites saludables"),
                new Categoria(null, "Cereales", "Cereales integrales"),
                new Categoria(null, "Verduras", "Verduras frescas"),
                new Categoria(null, "Higiene", "Productos de higiene personal"),
                new Categoria(null, "Bebidas", "Bebidas naturales"),
                new Categoria(null, "Snacks", "Snacks saludables")
        );
        categoriaRepository.saveAll(categorias);

        Random random = new Random();

        // Crear productos aleatorios
        for (int i = 0; i < 30; i++) {
            Categoria categoria = categorias.get(random.nextInt(categorias.size()));
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setMarca(faker.company().name());
            producto.setDescripcion(faker.lorem().sentence());
            producto.setPrecio(faker.number().randomDouble(2, 1, 100));
            producto.setStock(faker.number().numberBetween(10, 200));
            producto.setImagenUrl(faker.internet().url());
            producto.setCategoria(categoria);
            productoRepository.save(producto);
        }
    }
}