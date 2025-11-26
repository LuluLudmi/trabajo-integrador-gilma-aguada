// src/main/java/ar/edu/centro8/daw/trabajo_integrador_gilma_aguada/controller/PedidoController.java
package ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.controller;

import ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.model.Pedido;
import ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.model.PedidoDetalle;
import ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.crear(pedido);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.actualizar(id, pedido);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<PedidoDetalle> agregarDetalle(@PathVariable Integer id, @RequestBody PedidoDetalle detalle) {
        PedidoDetalle nuevoDetalle = pedidoService.agregarDetalle(id, detalle);
        return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estadoPago");
        Pedido pedidoActualizado = pedidoService.actualizarEstadoPago(id, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorCliente(@PathVariable Integer clienteId) {
        List<Pedido> pedidos = pedidoService.obtenerPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Pedido>> obtenerPendientes() {
        List<Pedido> pedidos = pedidoService.obtenerPendientes();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Map<String, BigDecimal>> calcularTotal(@PathVariable Integer id) {
        BigDecimal total = pedidoService.calcularTotal(id);
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("total", total);
        return ResponseEntity.ok(response);
    }
}