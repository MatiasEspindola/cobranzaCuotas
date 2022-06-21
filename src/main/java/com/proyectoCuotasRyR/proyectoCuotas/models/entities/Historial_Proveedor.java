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
@Table(name="historiales_proveedores")
public class Historial_Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_historial_proveedor;
	
	@JoinColumn(name = "fk_proveedor", referencedColumnName = "id_proveedor")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Proveedor proveedor;
	
	@JoinColumn(name = "id_actividad_proveedor", referencedColumnName = "id_actividad")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Actividad_Usuario actividad_usuario;

	public long getId_historial_proveedor() {
		return id_historial_proveedor;
	}

	public void setId_historial_proveedor(long id_historial_proveedor) {
		this.id_historial_proveedor = id_historial_proveedor;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Actividad_Usuario getActividad_usuario() {
		return actividad_usuario;
	}

	public void setActividad_usuario(Actividad_Usuario actividad_usuario) {
		this.actividad_usuario = actividad_usuario;
	}
	
	
	
}
