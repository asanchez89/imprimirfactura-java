package beans;

import java.math.BigDecimal;

public class Producto implements Comparable<Producto>{

	private String idProducto;
	private String nombre;
	private Integer cantidad;
	private BigDecimal precio;
	private BigDecimal precioOriginal;
	private BigDecimal total;
	private Integer index;
	
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public BigDecimal getPrecioOriginal() {
		return precioOriginal;
	}
	public void setPrecioOriginal(BigDecimal precioOriginal) {
		this.precioOriginal = precioOriginal;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio="
				+ precio + ", precioOriginal=" + precioOriginal + ", total=" + total + ", index=" + index + "]";
	}
	@Override
	public int compareTo(Producto o) {
		return this.getIndex().compareTo(o.getIndex());
	}
	
}
