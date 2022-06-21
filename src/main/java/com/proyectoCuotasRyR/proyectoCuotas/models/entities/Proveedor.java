package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="proveedores")
public class Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_proveedor")
	private long id_proveedor;
	
	@Column(name="razon_social")
	private String razon_social;
	
	@JoinColumn(name = "id_tipodocumento1", referencedColumnName = "id_tipo_documento")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Tipo_Documento id_tipo_documento1;
	
	private String nro_documento;
	
	private String direccion;
	
	private String tel_fijo;
	
	private String tel_fijo2;
	
	private String cel;
	
	private String cel2;
	
	private String mail;
	
	private String mail2;
	
	private boolean activo;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_plan_pago")
	@JsonManagedReference
	@JsonIgnore
	private List<Plan_Pago> planes_pagos;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_proveedor")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Proveedor> historiales;

	
	
	public List<Historial_Proveedor> getHistoriales() {
		return historiales;
	}

	public void setHistoriales(List<Historial_Proveedor> historiales) {
		this.historiales = historiales;
	}

	public long getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(long id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public List<Plan_Pago> getPlanes_pagos() {
		return planes_pagos;
	}

	public void setPlanes_pagos(List<Plan_Pago> planes_pagos) {
		this.planes_pagos = planes_pagos;
	}



	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	@JoinColumn(name = "id_localidad4", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Localidad id_localidad4;



	public Localidad getId_localidad4() {
		return id_localidad4;
	}

	public void setId_localidad4(Localidad id_localidad4) {
		this.id_localidad4 = id_localidad4;
	}

	public Tipo_Documento getId_tipo_documento1() {
		return id_tipo_documento1;
	}

	public void setId_tipo_documento1(Tipo_Documento id_tipo_documento1) {
		this.id_tipo_documento1 = id_tipo_documento1;
	}

	public String getNro_documento() {
		return nro_documento;
	}

	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return razon_social + ", (" + id_localidad4.getLocalidad() + ", " + id_localidad4.getProvincia().getProv() + ")";
	}
	
	
	
	
	
}
