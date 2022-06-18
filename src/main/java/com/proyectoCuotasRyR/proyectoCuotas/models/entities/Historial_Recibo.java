package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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

	private String concepto;
	
	@JoinColumn(name = "id_actividad_recibo", referencedColumnName = "id_actividad")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Actividad_Usuario actividad_usuario;
	
	@JoinColumn(name = "id_ctactecliente_recibo", referencedColumnName = "id_ctactecliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private CtaCteCliente ctactecliente;

	public long getId_historial_recibo() {
		return id_historial_recibo;
	}

	public void setId_historial_recibo(long id_historial_recibo) {
		this.id_historial_recibo = id_historial_recibo;
	}

	public Recibo getRecibo() {
		return recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
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
