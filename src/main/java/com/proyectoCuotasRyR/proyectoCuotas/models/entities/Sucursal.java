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
	
	
	
	private String direccion;
	
	private boolean es_casa_central;

	
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Usuario usuario;
	
	
	@JoinColumn(name = "id_localidad2", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Localidad id_localidad2;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_sucursal")
	@JsonManagedReference
	@JsonIgnore
	private List<Plan_Pago> planes_pagos;
	
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



	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Plan_Pago> getPlanes_pagos() {
		return planes_pagos;
	}

	public void setPlanes_pagos(List<Plan_Pago> planes_pagos) {
		this.planes_pagos = planes_pagos;
	}
	
	

}
