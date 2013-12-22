package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Persona {
	private int Codigo;
	private String Cedula;
	private String Nombre;
	private String Apellido;
	private String Direccion;
//	private Date FecNac;
	private String Telefono;
//	private String Celular;
//	private String Correo;
//	private Character Sexo;
	private Character Estado;
	private Ciudad PerCiudad;
	private Oficina	PerOficina;
//	private List<Imagen> Imagenes;

	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}

	public void setCodigo(int id) {
		Codigo = id;
	}

	public String getCedula() {
		return Cedula;
	}

	public void setCedula(String cedula) {
		Cedula = cedula;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	
/*	public Date getFecNac() {
		return FecNac;
	}

	public void setFecNac(Date fecNac) {
		FecNac = fecNac;
	}
*/
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	
	
/*	public String getCelular() {
		return Celular;
	}
	public void setCelular(String celular) {
		Celular = celular;
	}

	public String getCorreo() {
		return Correo;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}	
	public Character getSexo() {
		return Sexo;
	}
	public void setSexo(Character sexo) {
		Sexo = sexo;
	}
*
*/
	public Character getEstado() {
		return Estado;
	}
	public void setEstado(Character estado) {
		Estado = estado;
	}

	public void setPerCiudad(Ciudad perCiudad) {
		PerCiudad = perCiudad;
	}

	@ManyToOne
	@JoinColumn(name="CodCiudad")
	public Ciudad getPerCiudad() {
		return PerCiudad;
	}

	@ManyToOne
	@JoinColumn(name="CodOficina")
	public Oficina getPerOficina() {
		return PerOficina;
	}

	public void setPerOficina(Oficina perOficina) {
		PerOficina = perOficina;
	}

	
/*	@OneToMany(targetEntity = Imagen.class,
			mappedBy = "persona",
			cascade=CascadeType.ALL, 
			fetch=FetchType.EAGER)
	public List<Imagen> getImagenes() {
		return Imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.Imagenes = imagenes;
	}
*/
}
