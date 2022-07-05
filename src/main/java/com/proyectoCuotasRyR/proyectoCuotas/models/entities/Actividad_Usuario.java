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
@Table(name="actividades_usuarios")
public class Actividad_Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_actividad;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date hora;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	@JoinColumn(name = "id_usuario_sucursal", referencedColumnName = "id_usuario_sucursal")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Usuario_Sucursal usuario;
	
	private String descripcion;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_actividad_recibo")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Recibo> historial_recibos;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_actividad_cliente")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Alta_Cliente> historial_clientes;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_actividad_plan_pago")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Plan_Pago> historial_planes_pagos;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_actividad_sucursal")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Sucursal> historial_sucursales;

	

	public long getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(long id_actividad) {
		this.id_actividad = id_actividad;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	

	public Usuario_Sucursal getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario_Sucursal usuario) {
		this.usuario = usuario;
	}

	public List<Historial_Recibo> getHistorial_recibos() {
		return historial_recibos;
	}

	public void setHistorial_recibos(List<Historial_Recibo> historial_recibos) {
		this.historial_recibos = historial_recibos;
	}

	public List<Historial_Alta_Cliente> getHistorial_clientes() {
		return historial_clientes;
	}

	public void setHistorial_clientes(List<Historial_Alta_Cliente> historial_clientes) {
		this.historial_clientes = historial_clientes;
	}

	public List<Historial_Plan_Pago> getHistorial_planes_pagos() {
		return historial_planes_pagos;
	}

	public void setHistorial_planes_pagos(List<Historial_Plan_Pago> historial_planes_pagos) {
		this.historial_planes_pagos = historial_planes_pagos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Historial_Sucursal> getHistorial_sucursales() {
		return historial_sucursales;
	}

	public void setHistorial_sucursales(List<Historial_Sucursal> historial_sucursales) {
		this.historial_sucursales = historial_sucursales;
	}
	
	
	
	

}
