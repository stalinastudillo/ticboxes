package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Facturacion {
	private int Codigo;
	private String Rif;
	private String Razon;
//	private Dirección FactDireccion;
	private String Telefono;
	private String Direccion;
	private String Correo;
	private Ciudad FacCiudad;
	private Oficina FacOficina;

	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int id) {
		Codigo = id;
	}
	public void setRif(String rif) {
		Rif = rif;
	}
	public String getRif() {
		return Rif;
	}
	public String getRazon() {
		return Razon;
	}
	public void setRazon(String razon) {
		Razon = razon;
	}

	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion1) {
		Direccion = direccion1;
	}
	
/*	@OneToMany
	@Column(name="CodDireccion")
	public Direccion getFactDireccion() {
		return FactDireccion;
	}
	public void setFactDireccion(Direccion direccion1) {
		FactDireccion = direccion1;
	}
*/
	
/*	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
*/

	
	public String getCorreo() {
		return Correo;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}

	@ManyToOne
	@JoinColumn(name="CodCiudad")
	public Ciudad getFacCiudad() {
		return FacCiudad;
	}

	public void setFacCiudad(Ciudad facCiudad) {
		FacCiudad = facCiudad;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getTelefono() {
		return Telefono;
	}
	
	@ManyToOne
	@JoinColumn(name="CodOficina")
	public Oficina getFacOficina() {
		return FacOficina;
	}
	public void setFacOficina(Oficina facOficina) {
		FacOficina = facOficina;
	}
	
	
}
