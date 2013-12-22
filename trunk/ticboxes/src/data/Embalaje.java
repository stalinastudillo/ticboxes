package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Embalaje {
	private int Codigo;
	private String Descripcion;
	private Boolean Ipostel;
	
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

	@Column(nullable=false,columnDefinition="boolean default false")
	public Boolean getIpostel() 
	{
		return Ipostel;
	}
	
	public void setIpostel(Boolean ipostel)
	{
		Ipostel = ipostel;
	}
	
}
