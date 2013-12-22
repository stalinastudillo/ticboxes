package data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Oficina {
	private int Codigo;
	private String Descripcion;
	private Character SerieFiscal;
	private Boolean Activa;
	
	@Id
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
	
	public Character getSerieFiscal() {
		return SerieFiscal;
	}
	public void setSerieFiscal(Character serieFiscal) {
		SerieFiscal = serieFiscal;
	}
	
	public void setActiva(Boolean activa) {
		Activa = activa;
	}
	public Boolean getActiva() {
		return Activa;
	}
}
