package beans;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Factura {

	private Long idFactura;
	private Long seqFactura;
	private String idEstadoCuenta;
	private Integer tipoFactura;
	private Integer idStatus;
	private String cedula;
	private String razonSocial;
	private String direccion;
	private String control;
	private String fechaRegistro;
	private String aseguradora;
	private BigDecimal jubilaDescuento;
	private BigDecimal seguroDescuento;
	private BigDecimal seguroReintegro;
	private BigDecimal totalPagado;
	private ArrayList<Concepto> conceptos;
	private ArrayList<Producto> productos;
	
	public Long getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}
	public Long getSeqFactura() {
		return seqFactura;
	}
	public void setSeqFactura(Long seqFactura) {
		this.seqFactura = seqFactura;
	}
	public String getIdEstadoCuenta() {
		return idEstadoCuenta;
	}
	public void setIdEstadoCuenta(String idEstadoCuenta) {
		this.idEstadoCuenta = idEstadoCuenta;
	}
	public Integer getTipoFactura() {
		return tipoFactura;
	}
	public void setTipoFactura(Integer tipoFactura) {
		this.tipoFactura = tipoFactura;
	}
	public Integer getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public BigDecimal getJubilaDescuento() {
		return jubilaDescuento;
	}
	public void setJubilaDescuento(BigDecimal jubilaDescuento) {
		this.jubilaDescuento = jubilaDescuento;
	}
	public BigDecimal getSeguroDescuento() {
		return seguroDescuento;
	}
	public void setSeguroDescuento(BigDecimal seguroDescuento) {
		this.seguroDescuento = seguroDescuento;
	}
	public BigDecimal getSeguroReintegro() {
		return seguroReintegro;
	}
	public void setSeguroReintegro(BigDecimal seguroReintegro) {
		this.seguroReintegro = seguroReintegro;
	}
	public BigDecimal getTotalPagado() {
		return totalPagado;
	}
	public void setTotalPagado(BigDecimal totalPagado) {
		this.totalPagado = totalPagado;
	}
	public ArrayList<Concepto> getConceptos() {
		return conceptos;
	}
	public void setConceptos(ArrayList<Concepto> conceptos) {
		this.conceptos = conceptos;
	}
	public ArrayList<Producto> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
	public String getAseguradora() {
		return aseguradora==null?"0":aseguradora;
	}
	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}
	@Override
	public String toString() {
		return "Factura [idFactura=" + idFactura + ", seqFactura=" + seqFactura + ", idEstadoCuenta=" + idEstadoCuenta
				+ ", tipoFactura=" + tipoFactura + ", idStatus=" + idStatus + ", cedula=" + cedula + ", razonSocial="
				+ razonSocial + ", direccion=" + direccion + ", control=" + control + ", fechaRegistro=" + fechaRegistro
				+ ", aseguradora=" + aseguradora + ", jubilaDescuento=" + jubilaDescuento + ", seguroDescuento="
				+ seguroDescuento + ", seguroReintegro=" + seguroReintegro + ", totalPagado=" + totalPagado
				+ ", conceptos=" + conceptos + ", productos=" + productos + "]";
	}
	
}


