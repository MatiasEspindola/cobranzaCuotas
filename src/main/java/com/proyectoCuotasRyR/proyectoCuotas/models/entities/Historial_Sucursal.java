package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="historiales_sucursales")
public class Historial_Sucursal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_historial_sucursal;
	
	@JoinColumn(name = "fk_sucursal", referencedColumnName = "id_sucursal")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Sucursal sucursal;
	
	@JoinColumn(name = "id_actividad_sucursal", referencedColumnName = "id_actividad")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Actividad_Usuario actividad_usuario;

	public long getId_historial_sucursal() {
		return id_historial_sucursal;
	}

	public void setId_historial_sucursal(long id_historial_sucursal) {
		this.id_historial_sucursal = id_historial_sucursal;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Actividad_Usuario getActividad_usuario() {
		return actividad_usuario;
	}

	public void setActividad_usuario(Actividad_Usuario actividad_usuario) {
		this.actividad_usuario = actividad_usuario;
	}
	
}
