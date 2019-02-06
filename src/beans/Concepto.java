package beans;

import java.math.BigDecimal;

public class Concepto implements Comparable<Concepto> {
	
	private Long idConcepto;
	private String nombre;
	private Integer cantidad;
	private BigDecimal precio;
	private BigDecimal total;
	private String nombreCategoria;
	private BigDecimal descuento;
	private BigDecimal impuesto;
	private BigDecimal precioOriginal;
	private Integer idTipo;
	private String descontable;
	private String facturable;
	private String seguroaumento;
	private String segurodescuento;
	private Integer index;
	
	public Long getIdConcepto() {
		return idConcepto;
	}
	public void setIdConcepto(Long idConcepto) {
		this.idConcepto = idConcepto;
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
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public Integer getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}
	public BigDecimal getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}
	public BigDecimal getPrecioOriginal() {
		return precioOriginal;
	}
	public void setPrecioOriginal(BigDecimal precioOriginal) {
		this.precioOriginal = precioOriginal;
	}
	public String getDescontable() {
		return descontable;
	}
	public void setDescontable(String descontable) {
		this.descontable = descontable;
	}
	public String getFacturable() {
		return facturable;
	}
	public void setFacturable(String facturable) {
		this.facturable = facturable;
	}
	/**
	 * @return the seguroaumento
	 */
	public String getSeguroaumento() {
		return seguroaumento;
	}
	/**
	 * @param seguroaumento the seguroaumento to set
	 */
	public void setSeguroaumento(String seguroaumento) {
		this.seguroaumento = seguroaumento;
	}
	/**
	 * @return the segurodescuento
	 */
	public String getSegurodescuento() {
		return segurodescuento;
	}
	/**
	 * @param segurodescuento the segurodescuento to set
	 */
	public void setSegurodescuento(String segurodescuento) {
		this.segurodescuento = segurodescuento;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "Concepto [idConcepto=" + idConcepto + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio="
				+ precio + ", total=" + total + ", nombreCategoria=" + nombreCategoria + ", descuento=" + descuento
				+ ", impuesto=" + impuesto + ", precioOriginal=" + precioOriginal + ", idTipo=" + idTipo
				+ ", descontable=" + descontable + ", facturable=" + facturable + ", seguroaumento=" + seguroaumento
				+ ", segurodescuento=" + segurodescuento + ", index=" + index + "]";
	}
	@Override
	public int compareTo(Concepto o) {
		return this.getIndex().compareTo(o.getIndex());
	}
	
	

	
}
