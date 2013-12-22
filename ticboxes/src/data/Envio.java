package data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Envio {
	private int Codigo;
	private String Origen;
	private int CodOficina;
	private String Destino;
	private String Embalaje;
	private Double Peso;
	private String Unidad;
	private Double Monto;
	private Boolean FleteDestino;
	private String RemCedula;
	private String RemNombre;
	private String RemRif;
	private String RemRazon;
	private String DesCedula;
	private String DesNombre;
	private String DesRif;
	private String DesRazon;
	private int Factura;
	private Date Fecha;
	private Character Estado;
	private int Id_0;
	private int Id_1;
	private int Id_2;

	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	public String getOrigen() {
		return Origen;
	}
	public void setOrigen(String origen) {
		Origen = origen;
	}
	
	public String getDestino() {
		return Destino;
	}
	public void setDestino(String destino) {
		Destino = destino;
	}
	
	public String getEmbalaje() {
		return Embalaje;
	}
	public void setEmbalaje(String embalaje) {
		Embalaje = embalaje;
	}
	
	public Double getPeso() {
		return Peso;
	}
	public void setPeso(Double peso) {
		Peso = peso;
	}
	
	public Double getMonto() {
		return Monto;
	}
	public void setMonto(Double monto) {
		Monto = monto;
	}
	
	public Boolean getFleteDestino() {
		return FleteDestino;
	}
	public void setFleteDestino(Boolean fleteDestino) {
		FleteDestino = fleteDestino;
	}
	
	public String getRemCedula() {
		return RemCedula;
	}
	public void setRemCedula(String remCedula) {
		RemCedula = remCedula;
	}
	
	public String getRemNombre() {
		return RemNombre;
	}
	public void setRemNombre(String remNombre) {
		RemNombre = remNombre;
	}
	
	public String getRemRif() {
		return RemRif;
	}
	public void setRemRif(String remRif) {
		RemRif = remRif;
	}
	
	public String getRemRazon() {
		return RemRazon;
	}
	public void setRemRazon(String remRazon) {
		RemRazon = remRazon;
	}
	
	public String getDesCedula() {
		return DesCedula;
	}
	public void setDesCedula(String desCedula) {
		DesCedula = desCedula;
	}
	
	public String getDesNombre() {
		return DesNombre;
	}
	public void setDesNombre(String desNombre) {
		DesNombre = desNombre;
	}
	
	public String getDesRif() {
		return DesRif;
	}
	public void setDesRif(String desRif) {
		DesRif = desRif;
	}
	
	public String getDesRazon() {
		return DesRazon;
	}
	public void setDesRazon(String desRazon) {
		DesRazon = desRazon;
	}
	
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	
	public Character getEstado() {
		return Estado;
	}
	public void setEstado(Character estado) {
		Estado = estado;
	}
	public int getId_0() {
		return Id_0;
	}
	public void setId_0(int id_0) {
		Id_0 = id_0;
	}
	public int getId_1() {
		return Id_1;
	}
	public void setId_1(int id_1) {
		Id_1 = id_1;
	}
	public int getId_2() {
		return Id_2;
	}
	public void setId_2(int id_2) {
		Id_2 = id_2;
	}
	public void setFactura(int i) {
		Factura = i;
	}
	public int getFactura() {
		return Factura;
	}
	public void setUnidad(String unidad) {
		Unidad = unidad;
	}
	public String getUnidad() {
		return Unidad;
	}
	public void setCodOficina(int codOficina) {
		CodOficina = codOficina;
	}
	public int getCodOficina() {
		return CodOficina;
	}

	@Transient
	public Envio getNewEnvio(Envio env)
	{
		Envio xx = new Envio();

		xx.setOrigen(env.getOrigen());
		xx.setCodOficina(env.getCodOficina());;
		xx.setDestino(env.getDestino());
		xx.setEmbalaje(env.getEmbalaje());
		xx.setPeso(env.getPeso());
		xx.setUnidad(env.getUnidad());
		xx.setMonto(env.getMonto());
		xx.setFleteDestino(env.getFleteDestino());
		xx.setRemCedula(env.getRemCedula());
		xx.setRemNombre(env.getRemNombre());
		xx.setRemRif(env.getRemRif());
		xx.setRemRazon(env.getRemRazon());
		xx.setDesCedula(env.getDesCedula());
		xx.setDesNombre(env.getDesNombre());
		xx.setDesRif(env.getDesRif());
		xx.setDesRazon(env.getDesRazon());
		xx.setFactura(env.getFactura());
		xx.setFecha(env.getFecha());
		xx.setEstado(env.getEstado());
		xx.setId_0(env.getId_0());
		xx.setId_1(env.getId_1());
		xx.setId_2(env.getId_2());		
		return xx; 
	}
	
}
