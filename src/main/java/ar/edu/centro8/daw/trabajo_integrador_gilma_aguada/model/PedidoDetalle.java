
package ar.edu.centro8.daw.trabajo_integrador_gilma_aguada.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedido_detalle")
@Access(AccessType.FIELD)
public class PedidoDetalle {

    @EmbeddedId
    private PedidoDetalleId id = new PedidoDetalleId();

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 38, scale = 2)
    private BigDecimal precioUnitario;

    public PedidoDetalle() {
    }

    public PedidoDetalle(Pedido pedido, Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        // No seteamos id manualmente; MapsId lo resuelve
    }

    public PedidoDetalleId getId() {
        return id;
    }

    public void setId(PedidoDetalleId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}