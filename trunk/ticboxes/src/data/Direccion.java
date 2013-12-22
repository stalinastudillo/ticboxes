package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Direccion {
	private int Codigo;
//	private Persona persona;
	private String Urb;
	private String Calle;
	private String Casa;
//	private String Numero;
//	private String Sector;
	private String Telefono;
	private int Codciudad;

	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
/*	@ManyToOne
	@JoinColumn(name="Cod_Persona")
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona1) {
		this.persona = persona1;
	}
*/
	
	public String getUrb() {
		return Urb;
	}
	public void setUrb(String urb) {
		Urb = urb;
	}
	public String getCalle() {
		return Calle;
	}
	public void setCalle(String calle) {
		Calle = calle;
	}
	public String getCasa() {
		return Casa;
	}
	public void setCasa(String casa) {
		Casa = casa;
	}
/*	public String getNumero() {
		return Numero;
	}
	public void setNumero(String numero) {
		Numero = numero;
	}
	public String getSector() {
		return Sector;
	}
	public void setSector(String sector) {
		Sector = sector;
	}

*/	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public int getCodciudad() {
		return Codciudad;
	}
	public void setCodciudad(int codciudad) {
		Codciudad = codciudad;
	}

}
