package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="sucursales")
public class Sucursal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_sucursal;
	
	@JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Empresa empresa;
	
	private String tel_fijo;
	
	private String tel_fijo2;
	
	private String cel;
	
	private String cel2;
	
	private String mail;
	
	private String mail2;
	
	private boolean activo;
	
	private String nombre_sucursal;
	
	
	
	public String getNombre_sucursal() {
		return nombre_sucursal;
	}

	public void setNombre_sucursal(String nombre_sucursal) {
		this.nombre_sucursal = nombre_sucursal;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	private String direccion;
	
	private boolean es_casa_central;
	
	private int puntoventaafip;

	
	public int getPuntoventaafip() {
		return puntoventaafip;
	}

	public void setPuntoventaafip(int puntoventaafip) {
		this.puntoventaafip = puntoventaafip;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_sucursal")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Sucursal> historiales;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_sucursal")
	@JsonManagedReference
	@JsonIgnore
	private List<Usuario_Sucursal> usuarios_sucursales;
	
	
	
	public List<Historial_Sucursal> getHistoriales() {
		return historiales;
	}

	public void setHistoriales(List<Historial_Sucursal> historiales) {
		this.historiales = historiales;
	}

	@JoinColumn(name = "id_localidad2", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Localidad id_localidad2;
	
	
	public Localidad getId_localidad2() {
		return id_localidad2;
	}

	public void setId_localidad2(Localidad id_localidad2) {
		this.id_localidad2 = id_localidad2;
	}

	public long getId_sucursal() {
		return id_sucursal;
	}

	public void setId_sucursal(long id_sucursal) {
		this.id_sucursal = id_sucursal;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getTel_fijo() {
		return tel_fijo;
	}

	public void setTel_fijo(String tel_fijo) {
		this.tel_fijo = tel_fijo;
	}

	public String getTel_fijo2() {
		return tel_fijo2;
	}

	public void setTel_fijo2(String tel_fijo2) {
		this.tel_fijo2 = tel_fijo2;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getCel2() {
		return cel2;
	}

	public void setCel2(String cel2) {
		this.cel2 = cel2;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}




	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean isEs_casa_central() {
		return es_casa_central;
	}

	public void setEs_casa_central(boolean es_casa_central) {
		this.es_casa_central = es_casa_central;
	}

	public List<Usuario_Sucursal> getUsuarios_sucursales() {
		return usuarios_sucursales;
	}

	public void setUsuarios_sucursales(List<Usuario_Sucursal> usuarios_sucursales) {
		this.usuarios_sucursales = usuarios_sucursales;
	}
	
	

}
