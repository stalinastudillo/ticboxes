package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tarifa {
	private int Codigo;
	private String Descripcion;
	private Unidad UndMedida;
	private Boolean TarIpostel;
	private double Desde;
	private double Hasta;
	private double Monto;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public double getDesde() {
		return Desde;
	}
	public void setDesde(double desde) {
		Desde = desde;
	}
	public double getHasta() {
		return Hasta;
	}
	public void setHasta(double hasta) {
		Hasta = hasta;
	}
	public double getMonto() {
		return Monto;
	}
	public void setMonto(double monto) {
		Monto = monto;
	}
	
	@ManyToOne
	@JoinColumn(name="CodUnidad")
	public Unidad getUndMedida() {
		return UndMedida;
	}

	public void setUndMedida(Unidad undMedida) {
		UndMedida = undMedida;
	}

	@Column(nullable=false,columnDefinition="boolean default false")
	public Boolean getTarIpostel() {
		return TarIpostel;
	}
	
	public void setTarIpostel(Boolean tarIpostel) {
		TarIpostel = tarIpostel;
	}	

}
