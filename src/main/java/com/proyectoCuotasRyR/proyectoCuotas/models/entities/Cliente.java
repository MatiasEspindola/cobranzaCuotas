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
import javax.persistence.OneToOne;
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
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_cliente;

	private String cliente;
	private String nro_documento;

	@JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Tipo_Documento tipo_documento;
	
	@JoinColumn(name = "id_responsable1", referencedColumnName = "id_responsable")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Responsable_Iva id_responsable;
	

	
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cliente")
	@JsonManagedReference
	@JsonIgnore
	private List<CtaCteCliente> ctasctesclientes;
	
	
	
	public List<CtaCteCliente> getCtasctesclientes() {
		return ctasctesclientes;
	}

	public void setCtasctesclientes(List<CtaCteCliente> ctasctesclientes) {
		this.ctasctesclientes = ctasctesclientes;
	}




	private boolean activo;
	

	

	private String direccion;

	private String tel_fijo;

	private String tel_fijo2;

	private String cel;

	private String cel2;

	private String mail;

	private String mail2;

	public long getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNro_documento() {
		return nro_documento;
	}

	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}

	public Tipo_Documento getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(Tipo_Documento tipo_documento) {
		this.tipo_documento = tipo_documento;
	}
	
	

	@JoinColumn(name = "id_localidad1", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Localidad id_localidad1;

	public Localidad getId_localidad1() {
		return id_localidad1;
	}

	public void setId_localidad1(Localidad id_localidad1) {
		this.id_localidad1 = id_localidad1;
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

	

	public Responsable_Iva getId_responsable() {
		return id_responsable;
	}

	public void setId_responsable(Responsable_Iva id_responsable) {
		this.id_responsable = id_responsable;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
