package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Imagen {
	private int Codigo;
	private byte[] File;
	private double Size;
//	private Persona persona;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
/*	@ManyToOne
	@JoinColumn(name="Id_Persona")
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
*/	
	@Lob
	public byte[] getFile() {
		return File;
	}
	public void setFile(byte[] file) {
		File = file;
	}
	
	public double getSize() {
		return Size;
	}
	public void setSize(double size) {
		Size = size;
	}
}
