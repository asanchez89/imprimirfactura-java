package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.Comando;
import beans.Concepto;
import beans.Factura;
import beans.Producto;
import tfhka.*;
import tfhka.pa.*;

public class Imprimir {
	final private Tfhka IMPRESORA;
	

	public Imprimir() {
		IMPRESORA = new Tfhka();
	}

	public static void main(String[] args) {
		Imprimir imprimir = new Imprimir();
		imprimir.cerrarPuerto();
		
		Boolean status = imprimir.abrirPuerto(args[1]);
		
		boolean check = imprimir.verificarImpresora();
		System.out.println("Status Impresora: "+check);
		
		System.out.println(imprimir.verificarEstado());
		if(status){
			try {
				switch (args[0]) {
				case "1":
					sleep(15000L);
					imprimir.imprimirFactura(args);
					break;
				case "2":
					sleep(15000L);
					imprimir.reporteX();
					break;
				case "3":
					sleep(15000L);
					imprimir.reporteZ();
					break;
				case "4":
					sleep(15000L);
					imprimir.imprimirNotaDeCredito(args);
					break;
				default:
					System.out.println("Opción de impresión inválida. ");
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (PrinterException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
			System.out.println(imprimir.verificarEstado());
		}
		System.exit(0);
	}

	private void imprimirFactura(String[] args) throws FileNotFoundException, IOException, ParseException, PrinterException, InterruptedException {
		Factura factura = this.parseFactura(args[2]);
		this.enviarComando("jR"+factura.getCedula());
		this.enviarComando("jS"+factura.getRazonSocial());
		this.enviarComando("j1Direccion: "+(factura.getDireccion()==null?" ":factura.getDireccion()));
		String[] symbols = {" ","!","\"","#"};
		Map<Integer, Comando> mapCommands = this.ordernarConceptos(factura, symbols);
	    for (Map.Entry<Integer, Comando> entry : mapCommands.entrySet()) {
	    	this.enviarComando(entry.getValue().getCmdPrimario());
	    	if(entry.getValue().getCmdDescuento() != null)
	    		this.enviarComando(entry.getValue().getCmdDescuento());
        }
	   
	   this.enviarComando("101");
	}
	
	private void imprimirNotaDeCredito(String[] args) throws FileNotFoundException, IOException, ParseException, PrinterException, InterruptedException, java.text.ParseException {
		Factura factura = this.parseFactura(args[3]);
		this.enviarComando("jR"+factura.getCedula());
		this.enviarComando("jS"+factura.getRazonSocial());
		this.enviarComando("jF"+args[2]);
		this.enviarComando("j1Direccion: "+(factura.getDireccion()==null?" ":factura.getDireccion()));
		String[] symbols = {"d0","d1","d2","d3"};
		Map<Integer, Comando> mapCommands = this.ordernarConceptos(factura, symbols);
	    for (Entry<Integer, Comando> entry : mapCommands.entrySet()) {
	    	this.enviarComando(entry.getValue().getCmdPrimario());
	    	if(entry.getValue().getCmdDescuento() != null)
	    		this.enviarComando(entry.getValue().getCmdDescuento());
        }
	   
	    this.enviarComando("113");
	}

	private Map<Integer, Comando> ordernarConceptos(Factura factura, String [] symbols) {
		ArrayList<Producto> productos = factura.getProductos();
		ArrayList<Concepto> conceptos = factura.getConceptos();
		DecimalFormat precioFormat = new DecimalFormat("00000000.00");
	    DecimalFormat cantidadFormat = new DecimalFormat("00000.000");
	    DecimalFormat descuentoFormat = new DecimalFormat("00.00");
	    Map<Integer, Comando> mapCommands = new TreeMap<>();
	    if(productos != null && !productos.isEmpty())
	    	Collections.sort(productos);
	    if(conceptos != null && !conceptos.isEmpty())
	    	Collections.sort(conceptos);
		if(productos != null) {
			for (Producto producto : productos) {
				int index = producto.getIndex();
				BigDecimal descuento = (factura.getAlquilerDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getAlquilerDescuento():((factura.getSeguroDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getSeguroDescuento():(factura.getJubilaDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getJubilaDescuento():BigDecimal.ZERO).divide(new BigDecimal(100));
				BigDecimal precioAnterior = producto.getPrecioOriginal()==null?producto.getPrecio().divide(BigDecimal.ONE.subtract(descuento), RoundingMode.HALF_EVEN):producto.getPrecioOriginal();
				String precioF = precioFormat.format((factura.getAlquilerReintegro().compareTo(BigDecimal.ZERO)==1)?precioAnterior.multiply(factura.getAlquilerReintegro().add(new BigDecimal(100)).divide(new BigDecimal(100))):precioAnterior).replace(".", "").replace(",", "");
				String cantidadF = cantidadFormat.format(producto.getCantidad()).replace(".", "").replace(",", "");
				String cmdString = symbols[0]+precioF+cantidadF+producto.getNombre();
				//System.out.println(cmdString);
				
				Comando comando = new Comando();
				comando.setCmdPrimario(cmdString);
				comando.setIndex(index);
				mapCommands.put(index, comando);
				
				if(descuento.compareTo(BigDecimal.ZERO)==1) {
					String descuentoF = descuentoFormat.format(descuento.multiply(new BigDecimal(100))).replace(".", "").replace(",", "");
					String descCommand = "p-"+descuentoF;
					//System.out.println(descCommand);
					comando.setCmdDescuento(descCommand);
				}
			}
	    }
		
	    if(conceptos != null) {
			for (Concepto concepto : conceptos) {
				if(concepto.getFacturable().equals("0")) {
					continue;
				}
				int index = concepto.getIndex();
				BigDecimal descuento = ((factura.getAlquilerDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getAlquilerDescuento():((factura.getSeguroDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getSeguroDescuento():(factura.getJubilaDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getJubilaDescuento():BigDecimal.ZERO));
				
				BigDecimal descuentoConcepto = (factura.getAlquilerDescuento().compareTo(BigDecimal.ZERO)==1)?factura.getAlquilerDescuento():((factura.getJubilaDescuento().compareTo(BigDecimal.ZERO)==0) && factura.getAseguradora().equals("0") && (concepto.getDescuento().compareTo(BigDecimal.ZERO)==0) ? descuento : ((factura.getJubilaDescuento().compareTo(BigDecimal.ZERO)==1) && factura.getAseguradora().equals("0") && (concepto.getDescuento().compareTo(BigDecimal.ZERO)==1) ? concepto.getDescuento() : BigDecimal.ZERO));
				descuentoConcepto = descuentoConcepto.compareTo(BigDecimal.ZERO)==1 || (concepto.getSegurodescuento().equals("1") && !factura.getAseguradora().equals("0"))?  descuentoConcepto : descuento;
				descuentoConcepto = concepto.getSegurodescuento().equals("1") && !factura.getAseguradora().equals("0") ?  descuentoConcepto : factura.getAseguradora().equals("0") ? descuentoConcepto : descuento;
				descuentoConcepto = concepto.getDescontable().equals("0") ? descuentoConcepto.divide(new BigDecimal(100)):BigDecimal.ZERO;
				BigDecimal precioAnterior = concepto.getPrecioOriginal()==null?concepto.getPrecio().divide(BigDecimal.ONE.add(concepto.getImpuesto().divide(new BigDecimal(100))), RoundingMode.HALF_EVEN).divide(BigDecimal.ONE.subtract(descuentoConcepto), RoundingMode.HALF_EVEN):concepto.getPrecioOriginal();
				
				String precioF = precioFormat.format((factura.getAlquilerReintegro().compareTo(BigDecimal.ZERO)==1)?precioAnterior.multiply(factura.getAlquilerReintegro().add(new BigDecimal(100)).divide(new BigDecimal(100))):precioAnterior).replace(".", "").replace(",", "");
				String cantidadF = cantidadFormat.format(concepto.getCantidad()).replace(".", "").replace(",", "");
				String taxSymbol = (concepto.getImpuesto().compareTo(BigDecimal.ZERO)==0)?symbols[0]:symbols[1];
				if(concepto.getImpuesto().compareTo(new BigDecimal(7))==0)
					taxSymbol = symbols[1];
				else if(concepto.getImpuesto().compareTo(new BigDecimal(9))==0)
					taxSymbol = symbols[2];
				else if(concepto.getImpuesto().compareTo(new BigDecimal(15))==0)
					taxSymbol = symbols[3];
				
				String cmdString = taxSymbol+precioF+cantidadF+concepto.getNombre();
				//System.out.println(cmdString);
				
				Comando comando = new Comando();
				comando.setCmdPrimario(cmdString);
				comando.setIndex(index);
				mapCommands.put(index, comando);
			
				if(concepto.getDescontable().equals("0") && descuentoConcepto.compareTo(BigDecimal.ZERO)==1) {
					String descuentoF = descuentoFormat.format(descuentoConcepto.multiply(new BigDecimal(100))).replace(".", "").replace(",", "");
					String descCommand = "p-"+descuentoF;
					//System.out.println(descCommand);
					comando.setCmdDescuento(descCommand);
				}
			}
	    }
		return mapCommands;
	}

	public Factura parseFactura(String jsonStringTest) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(jsonStringTest));
		JSONObject facturaJson = (JSONObject)obj;
		Factura factura = new Factura();
		factura.setIdFactura(Long.parseLong((String) facturaJson.get("idFactura")));
		factura.setSeqFactura((Long) facturaJson.get("seqFactura"));
		factura.setIdEstadoCuenta((String) facturaJson.get("idestadocuenta"));
		factura.setTipoFactura(Integer.parseInt((String) facturaJson.get("tipofactura")));
		factura.setIdStatus(Integer.parseInt((String) facturaJson.get("idstatus")));
		factura.setCedula((String) facturaJson.get("cedula"));
		factura.setRazonSocial((String) facturaJson.get("nombres"));
		factura.setDireccion((String) facturaJson.get("direccion"));
		factura.setFechaRegistro((String) facturaJson.get("fechaRegistro"));
		factura.setAseguradora((String) facturaJson.get("aseguradora"));
		factura.setJubilaDescuento(new BigDecimal((String) facturaJson.get("jubila-descuento")));
		factura.setSeguroDescuento(new BigDecimal((String) facturaJson.get("seguro-descuento")));
		factura.setSeguroReintegro(new BigDecimal((String) facturaJson.get("seguro-reintegro")));
		factura.setAlquilerDescuento(new BigDecimal((String) facturaJson.get("alquiler-descuento")));
		factura.setAlquilerReintegro(new BigDecimal((String) facturaJson.get("alquiler-reintegro")));
		ArrayList<Concepto> conceptoList = new ArrayList<>();
		ArrayList<Producto> productoList = new ArrayList<>();
		
		try {
			if (facturaJson.get("conceptos") != null && (facturaJson.get("conceptos") instanceof JSONArray)) {
				JSONArray conceptos = (JSONArray) facturaJson.get("conceptos");
				@SuppressWarnings("rawtypes")
				Iterator iter = conceptos.iterator();
				while (iter.hasNext()) {
					JSONObject conceptoJson = (JSONObject)iter.next();
					Concepto concepto = new Concepto();
					concepto.setIdConcepto(Long.parseLong((String) conceptoJson.get("idConcepto")));
					concepto.setNombre((String) conceptoJson.get("nombre"));
					concepto.setCantidad(Integer.parseInt((String) conceptoJson.get("cantidad")));
					concepto.setPrecio((new BigDecimal((String) conceptoJson.get("precio"))));
					concepto.setPrecioOriginal((new BigDecimal((String) conceptoJson.get("preciooriginal"))));
					concepto.setTotal((new BigDecimal((String) conceptoJson.get("total"))));
					concepto.setNombreCategoria((String) conceptoJson.get("nombreCategoria"));
					concepto.setDescuento((new BigDecimal((String) conceptoJson.get("descuento"))));
					concepto.setImpuesto((new BigDecimal((String) conceptoJson.get("impuesto"))));
					concepto.setIdTipo(Integer.parseInt((String) conceptoJson.get("idTipo")));
					concepto.setDescontable((String) conceptoJson.get("descontable"));
					concepto.setFacturable((String) conceptoJson.get("facturable"));
					concepto.setSeguroaumento((String) conceptoJson.get("seguroaumento"));
					concepto.setSegurodescuento((String) conceptoJson.get("segurodescuento"));
					concepto.setIndex(Integer.parseInt((String) conceptoJson.get("indexfactura")));
					conceptoList.add(concepto);
				}
			}
			factura.setConceptos(conceptoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (facturaJson.get("productos") != null && (facturaJson.get("productos") instanceof JSONArray)) {
				JSONArray productos = (JSONArray) facturaJson.get("productos");
				@SuppressWarnings("rawtypes")
				Iterator iter = productos.iterator();
				while (iter.hasNext()) {
					JSONObject productosJson = (JSONObject)iter.next();
					Producto producto = new Producto();
					producto.setIdProducto((String) productosJson.get("idproducto"));
					producto.setNombre((String) productosJson.get("nombre"));
					producto.setCantidad(Integer.parseInt((String) productosJson.get("cantidad")));
					producto.setPrecio(new BigDecimal((String) productosJson.get("precio")));
					producto.setPrecioOriginal(new BigDecimal((String) productosJson.get("preciooriginal")));
					producto.setTotal(new BigDecimal((String) productosJson.get("total")));
					producto.setIndex(Integer.parseInt((String) productosJson.get("indexfactura")));
					productoList.add(producto);
					factura.setProductos(productoList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factura;
	}

	public boolean abrirPuerto(String puerto) {
		//String[] puertos = jssc.SerialPortList.getPortNames();
			boolean status = IMPRESORA.OpenFpctrl(puerto);
			System.out.println("Status Puerto "+puerto+": "+status);
			if (status)
				return status;
		return false;
	}
	
	public void cerrarPuerto() {
		//String[] puertos = jssc.SerialPortList.getPortNames();
		IMPRESORA.CloseFpctrl();
	}
	
	public boolean verificarImpresora() {
		return IMPRESORA.CheckFprinter();
	}

	public String verificarEstado() {
		PrinterStatus status = IMPRESORA.getPrinterStatus();
		String estado = "\nValidez de Error: " + status.getErrorValidity() + "\nStatus de Error Impresora: " + status.getPrinterErrorCode() + " "
				+ status.getPrinterErrorDescription() + "\nStatus de Impresora: " + status.getPrinterStatusCode() + " "
				+ status.getPrinterStatusDescription();
		return estado;
	}

	public boolean verificarGaveta() {
		return IMPRESORA.CheckDrawer();
	}

	public boolean enviarComando(String comando) throws PrinterException {
		boolean status = IMPRESORA.SendCmd(comando);
		System.out.println(comando+" --Comando enviado: "+status);
		return status;
	}
	
	public void reporteX() throws PrinterException {
		IMPRESORA.printXReport();
	}
	
	public void reporteZ() throws PrinterException {
		IMPRESORA.printZReport();
	}
	
	public static void sleep(Long time) throws InterruptedException{
		System.out.println("La impresión iniciará en "+(time/1000)+" segundos.");
		Thread.sleep(time);
	}

}

