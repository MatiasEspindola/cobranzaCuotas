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
@Table(name="historiales_altas_planes_pagos")
public class Historial_Plan_Pago {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_historial_alta_plan_pago;
	
	@JoinColumn(name = "fk_plan_pago", referencedColumnName = "id_plan_pago")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Plan_Pago plan_pago;
	
	private String concepto;
	
	@JoinColumn(name = "id_ctactecliente_plan_pago", referencedColumnName = "id_ctactecliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private CtaCteCliente ctactecliente;
	
	@JoinColumn(name = "id_actividad_plan_pago", referencedColumnName = "id_actividad")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Actividad_Usuario actividad_usuario;

	public long getId_historial_alta_plan_pago() {
		return id_historial_alta_plan_pago;
	}

	public void setId_historial_alta_plan_pago(long id_historial_alta_plan_pago) {
		this.id_historial_alta_plan_pago = id_historial_alta_plan_pago;
	}

	public Plan_Pago getPlan_pago() {
		return plan_pago;
	}

	public void setPlan_pago(Plan_Pago plan_pago) {
		this.plan_pago = plan_pago;
	}

	

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public CtaCteCliente getCtactecliente() {
		return ctactecliente;
	}

	public void setCtactecliente(CtaCteCliente ctactecliente) {
		this.ctactecliente = ctactecliente;
	}

	public Actividad_Usuario getActividad_usuario() {
		return actividad_usuario;
	}

	public void setActividad_usuario(Actividad_Usuario actividad_usuario) {
		this.actividad_usuario = actividad_usuario;
	}
	
	

}
