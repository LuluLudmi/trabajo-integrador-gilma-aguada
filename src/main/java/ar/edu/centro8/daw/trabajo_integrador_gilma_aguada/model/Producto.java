// src/main/java/ar/edu/centro8/daw/trabajo_integrador_gilma_aguada/model/Producto.java
package ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<PedidoDetalle> pedidoDetalles;

    public Producto() {
    }

    public Producto(String nombre, BigDecimal precio, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public void setPedidoDetalles(List<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }
}
