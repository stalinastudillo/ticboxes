package data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pago {

	private int Codigo;
	private String Forma;
	private Banco PagBanco;
	private Double Monto;
	private String Refere;
	private String TTarje;
	private Factura PagFactura;
	private Date Fecha;
	private Usuario PagUsuario;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	
	public String getForma() {
		return Forma;
	}
	
	public void setForma(String forma) {
		Forma = forma;
	}

	@ManyToOne
	@JoinColumn(name="CodBanco")
	public Banco getPagBanco() {
		return PagBanco;
	}
	
	public void setPagBanco(Banco banco1) {
		PagBanco = banco1;
	}
	
	public Double getMonto() {
		return Monto;
	}
	public void setMonto(Double monto) {
		Monto = monto;
	}
	
	public String getRefere() {
		return Refere;
	}
	public void setRefere(String refere) {
		Refere = refere;
	}
	
	public String getTTarje() {
		return TTarje;
	}
	public void setTTarje(String tTarje) {
		TTarje = tTarje;
	}
	
	@ManyToOne
	@JoinColumn(name="CodFactura")
	public Factura getPagFactura() {
		return PagFactura;
	}
	public void setPagFactura(Factura facturPago) {
		PagFactura = facturPago;
	}
	
	public Date getFecha() {
		return Fecha;
	}
	
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
		
	@ManyToOne
	@JoinColumn(name="CodUsuario")
	public Usuario getPagUsuario() {
		return PagUsuario;
	}	
	
	public void setPagUsuario(Usuario pagusuario) {
		PagUsuario = pagusuario;
	}

}
