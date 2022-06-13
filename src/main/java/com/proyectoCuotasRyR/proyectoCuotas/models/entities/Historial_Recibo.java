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
@Table(name="historiales_recibos")
public class Historial_Recibo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_historial_recibo;
	
	@JoinColumn(name = "fk_recibo", referencedColumnName = "id_recibo")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Recibo recibo;

	private String descripcion;
	
	@JoinColumn(name = "fk_ctactecliente_recibo", referencedColumnName = "id_ctactecliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private CtaCteCliente ctactecliente;
	
}
