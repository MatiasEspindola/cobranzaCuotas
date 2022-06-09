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
@Table(name="empresas")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_empresa;
	
	private String cuit;
	
	private String tel_fijo;
	
	private String tel_fijo2;
	
	private String cel;
	
	private String cel2;
	
	private String mail;
	
	private String mail2;
	
	private String razon_social;
	
	private String nombre_fantasia;
	
	private String direccion;
	
	private String numero_dgr;
	
	private String logo;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inicio_actividades;
	
	
	
	// private boolean multiple_suc;
	
	public Date getInicio_actividades() {
		return inicio_actividades;
	}

	public void setInicio_actividades(Date inicio_actividades) {
		this.inicio_actividades = inicio_actividades;
	}

	private int puntoventaafipcentral;
	
	

	public int getPuntoventaafipcentral() {
		return puntoventaafipcentral;
	}

	public void setPuntoventaafipcentral(int puntoventaafipcentral) {
		this.puntoventaafipcentral = puntoventaafipcentral;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_empresa")
	@JsonManagedReference
	@JsonIgnore
	private List<Plan_Pago> planes_pagos;

	public List<Plan_Pago> getPlanes_pagos() {
		return planes_pagos;
	}

	public void setPlanes_pagos(List<Plan_Pago> planes_pagos) {
		this.planes_pagos = planes_pagos;
	}

	@JoinColumn(name = "id_responsable", referencedColumnName = "id_responsable")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Responsable_Iva id_responsable;
	
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

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_empresa_user")
	@JsonManagedReference
	@JsonIgnore
	private List<Usuario> usuarios;

	public long getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(long id_empresa) {
		this.id_empresa = id_empresa;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getNombre_fantasia() {
		return nombre_fantasia;
	}

	public void setNombre_fantasia(String nombre_fantasia) {
		this.nombre_fantasia = nombre_fantasia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNumero_dgr() {
		return numero_dgr;
	}

	public void setNumero_dgr(String numero_dgr) {
		this.numero_dgr = numero_dgr;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
/*
	public boolean isMultiple_suc() {
		return multiple_suc;
	}

	public void setMultiple_suc(boolean multiple_suc) {
		this.multiple_suc = multiple_suc;
	}*/

	/*public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}*/


	public Responsable_Iva getId_responsable() {
		return id_responsable;
	}

	public void setId_responsable(Responsable_Iva id_responsable) {
		this.id_responsable = id_responsable;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
