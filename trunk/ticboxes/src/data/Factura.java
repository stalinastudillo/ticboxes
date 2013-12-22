package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Factura {
	private int Codigo;
	private Persona Remitente;
//	private Direccion Direccion1;
	private Facturacion Facturacion1;
	private Date Fecha;
	private String Control;
	private String Archivo;
	private int Numero;
	private Imagen Foto;
	private String Destinatario;
	private String DesCedula;
//	private Direccion Direccion2;
	private Facturacion Facturacion2;
	private Ciudad FacCiudad;
	private Embalaje FacEmbalaje;
	private Boolean FleteDestino;
	private int Cantidad;
	private Unidad UndMedida;
	private double Peso;
	private double Precio;
	private double MontoIpostel;
	private double Monto;
	private double Descuento;
	private String Descripcion;
	private double Iva;
	private double Total;
	private String Estado;
	private Oficina FacOficina;
	private Date FechaCorta;
//	private Direccion FDireccion;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	@ManyToOne
	@JoinColumn(name="CodRemitente")
	public Persona getRemitente() {
		return Remitente;
	}
	public void setRemitente(Persona remitente) {
		Remitente = remitente;
	}
	
/*	@ManyToOne
	@JoinColumn(name="CodDireccion1")
	public Direccion getDireccion1() {
		return Direccion1;
	}
	public void setDireccion1(Direccion direccion1) {
		Direccion1 = direccion1;
	}
*/	
	@ManyToOne
	@JoinColumn(name="CodFacturacion1")
	public Facturacion getFacturacion1() {
		return Facturacion1;
	}
	public void setFacturacion1(Facturacion facturacion1) {
		Facturacion1 = facturacion1;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public String getControl() {
		return Control;
	}
	public void setControl(String control) {
		Control = control;
	}
	public int getNumero() {
		return Numero;
	}
	public void setNumero(int numero) {
		Numero = numero;
	}
	
	@ManyToOne
	@JoinColumn(name="CodFoto")
	public Imagen getFoto() {
		return Foto;
	}
	public void setFoto(Imagen foto) {
		Foto = foto;
	}
	public String getDestinatario() {
		return Destinatario;
	}
	public void setDestinatario(String destinatario) {
		Destinatario = destinatario;
	}
	public String getDesCedula() {
		return DesCedula;
	}
	public void setDesCedula(String desCedula) {
		DesCedula = desCedula;
	}
	
/*	@ManyToOne
	@JoinColumn(name="CodDireccion2")
	public Direccion getDireccion2() {
		return Direccion2;
	}
	public void setDireccion2(Direccion direccion2) {
		Direccion2 = direccion2;
	}
*/	
	@ManyToOne
	@JoinColumn(name="CodFacturacion2")
	public Facturacion getFacturacion2() {
		return Facturacion2;
	}
	public void setFacturacion2(Facturacion facturacion2) {
		Facturacion2 = facturacion2;
	}
	
	@ManyToOne
	@JoinColumn(name="CodEmbalaje")
	public Embalaje getFacEmbalaje() {
		return FacEmbalaje;
	}
	public void setFacEmbalaje(Embalaje facEmbalaje) {
		FacEmbalaje = facEmbalaje;
	}
	
	public Boolean getFleteDestino() {
		return FleteDestino;
	}
	public void setFleteDestino(Boolean fleteDestino) {
		FleteDestino = fleteDestino;
	}
	
	public int getCantidad() {
		return Cantidad;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	
	@ManyToOne
	@JoinColumn(name="CodUnidad")
	public Unidad getUndMedida() {
		return UndMedida;
	}
	public void setUndMedida(Unidad undMedida) {
		UndMedida = undMedida;
	}
	public double getPeso() {
		return Peso;
	}
	public void setPeso(double peso) {
		Peso = peso;
	}
	public double getPrecio() {
		return Precio;
	}
	public void setPrecio(double precio) {
		Precio = precio;
	}
	public double getMontoIpostel() {
		return MontoIpostel;
	}
	public void setMontoIpostel(double montoIpostel) {
		MontoIpostel = montoIpostel;
	}
	public double getMonto() {
		return Monto;
	}
	public void setMonto(double monto) {
		Monto = monto;
	}
	public double getDescuento() {
		return Descuento;
	}
	public void setDescuento(double descuento) {
		Descuento = descuento;
	}
	public double getIva() {
		return Iva;
	}
	public void setIva(double iva) {
		Iva = iva;
	}
	public double getTotal() {
		return Total;
	}
	public void setTotal(double total) {
		Total = total;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	
	public String getArchivo() {
		return Archivo;
	}
	public void setArchivo(String archivo) {
		Archivo = archivo;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setFacCiudad(Ciudad facCiudad) {
		FacCiudad = facCiudad;
	}

	@ManyToOne
	@JoinColumn(name="CodCiudad")
	public Ciudad getFacCiudad() {
		return FacCiudad;
	}
	
	public void setFacOficina(Oficina facOficina) {
		FacOficina = facOficina;
	}
	
	@ManyToOne
	@JoinColumn(name="CodOficina")
	public Oficina getFacOficina() {
		return FacOficina;
	}
	
	@Transient
	public Date getFechaCorta() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String myDate = formatter.format(this.Fecha);
		Date date = null;
		try {
			date = (formatter.parse(myDate));
			System.out.println("Fecha de Factura:"+myDate+"   "+date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	

	

}
