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
	
	private String descripcion;
	
	@JoinColumn(name = "fk_ctactecliente_plan_pago", referencedColumnName = "id_ctactecliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private CtaCteCliente ctactecliente;

}
