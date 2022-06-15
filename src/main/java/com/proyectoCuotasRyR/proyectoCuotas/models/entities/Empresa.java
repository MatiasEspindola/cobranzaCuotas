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
	
	private String nombre_fantasia;
	
	private String razon_social;
	
	private String numero_dgr;
	
	private String logo;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inicio_actividades;
	


	@JoinColumn(name = "id_responsable", referencedColumnName = "id_responsable")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Responsable_Iva id_responsable;
	

	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_empresa")
	@JsonManagedReference
	@JsonIgnore
	private List<Sucursal> sucursales;

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

	public String getNombre_fantasia() {
		return nombre_fantasia;
	}

	public void setNombre_fantasia(String nombre_fantasia) {
		this.nombre_fantasia = nombre_fantasia;
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

	public Date getInicio_actividades() {
		return inicio_actividades;
	}

	public void setInicio_actividades(Date inicio_actividades) {
		this.inicio_actividades = inicio_actividades;
	}



	public Responsable_Iva getId_responsable() {
		return id_responsable;
	}

	public void setId_responsable(Responsable_Iva id_responsable) {
		this.id_responsable = id_responsable;
	}


	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}
	
	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}
	
}
