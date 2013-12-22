package data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	private int Codigo;
	private String Login;
	private String Clave;
	private Grupo grupo;
	private List<Seccion> secciones;
	private String Firma;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}

	public String getLogin() {
		return Login;
	}
	
	public void setLogin(String login) {
		Login = login;
	}
	
	public String getClave() {
		return Clave;
	}
	
	public void setClave(String clave) {
		Clave = clave;
	}
	
	@ManyToOne
	@JoinColumn(name="Id_Grupo")
	public Grupo getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Grupo grupo1) {
		grupo = grupo1;
	}

	@OneToMany
	public List<Seccion> getSecciones() {
		return secciones;
	}

	public void setSecciones(List<Seccion> secciones1) {
		secciones = secciones1;
	}

	public String getFirma() {
		return Firma;
	}

	public void setFirma(String firma) {
		Firma = firma;
	}
}
