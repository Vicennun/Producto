package com.ECOMARKET.Producto.Controller;

import com.ECOMARKET.Producto.Model.Categoria;
import com.ECOMARKET.Producto.Model.Producto;
import com.ECOMARKET.Producto.Service.ProductoService;
import com.ECOMARKET.Producto.assembler.ProductoModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar todos los productos",
        description = "Obtiene una lista completa de todos los productos registrados en el sistema.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Obtener producto por ID",
        description = "Devuelve los detalles de un producto específico según su identificador.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto encontrado y retornado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró un producto con el ID proporcionado."
            )
        }
    )
    public EntityModel<Producto> getProductoById(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        return assembler.toModel(producto);
    }

    @PostMapping(produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Registra un nuevo producto en el sistema.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Producto creado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos inválidos para crear el producto."
            )
        }
    )
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza los detalles de un producto existente.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto actualizado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró el producto a actualizar."
            )
        }
    )
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(updatedProducto.getId())).toUri())
                .body(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto existente por su ID.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Producto eliminado exitosamente."
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró el producto a eliminar."
            )
        }
    )
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            productoService.delete(id.longValue());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/nombre/{nombre}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos por nombre",
        description = "Obtiene todos los productos que coinciden con el nombre proporcionado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos filtrada por nombre obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByNombre(@PathVariable String nombre) {
        List<EntityModel<Producto>> productos = productoService.findByNombre(nombre)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByNombre(nombre)).withSelfRel());
    }

    @GetMapping(value = "/marca/{marca}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos por marca",
        description = "Obtiene todos los productos de una marca específica.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos filtrada por marca obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByMarca(@PathVariable String marca) {
        List<EntityModel<Producto>> productos = productoService.findByMarca(marca)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarca(marca)).withSelfRel());
    }

    @GetMapping(value = "/categoria/{categoriaId}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos por categoría",
        description = "Obtiene todos los productos de una categoría específica.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos filtrada por categoría obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró la categoría especificada."
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByCategoria(@PathVariable Integer categoriaId) {
        List<EntityModel<Producto>> productos = productoService.findByCategoriaId(categoriaId)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoria(categoriaId)).withSelfRel());
    }

    @GetMapping(value = "/precio/menor-que/{precio}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos con precio menor que",
        description = "Obtiene todos los productos cuyo precio es menor al valor proporcionado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos con precio menor al valor dado obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByPrecioMenor(@PathVariable Double precio) {
        List<EntityModel<Producto>> productos = productoService.findByPrecioMenorQue(precio)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByPrecioMenor(precio)).withSelfRel());
    }

    @GetMapping(value = "/precio/mayor-que/{precio}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos con precio mayor que",
        description = "Obtiene todos los productos cuyo precio es mayor al valor proporcionado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos con precio mayor al valor dado obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByPrecioMayor(@PathVariable Double precio) {
        List<EntityModel<Producto>> productos = productoService.findByPrecioMayorQue(precio)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByPrecioMayor(precio)).withSelfRel());
    }

    @GetMapping(value = "/stock/menor-que/{stock}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos con stock menor que",
        description = "Obtiene todos los productos cuyo stock es menor al valor proporcionado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos con stock menor al valor dado obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByStockMenor(@PathVariable Integer stock) {
        List<EntityModel<Producto>> productos = productoService.findByStockMenorQue(stock)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByStockMenor(stock)).withSelfRel());
    }

    @GetMapping(value = "/stock/mayor-que/{stock}", produces = { MediaTypes.HAL_JSON_VALUE, "application/json" })
    @Operation(
        summary = "Listar productos con stock mayor que",
        description = "Obtiene todos los productos cuyo stock es mayor al valor proporcionado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos con stock mayor al valor dado obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Producto>> getProductosByStockMayor(@PathVariable Integer stock) {
        List<EntityModel<Producto>> productos = productoService.findByStockMayorQue(stock)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByStockMayor(stock)).withSelfRel());
    }
}