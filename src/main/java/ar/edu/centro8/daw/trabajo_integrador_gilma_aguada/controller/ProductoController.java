// src/main/java/ar/edu/centro8/daw/trabajo_integrador_gilma_aguada/controller/ProductoController.java
package ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.controller;

import ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.model.Producto;
import ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/con-stock")
    public ResponseEntity<List<Producto>> obtenerConStock() {
        List<Producto> productos = productoService.obtenerConStock();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Producto>> obtenerConBajoStock() {
        List<Producto> productos = productoService.obtenerProductosConBajoStock();
        return ResponseEntity.ok(productos);
    }
}
