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
@Table(name="historiales_altas_clientes")
public class Historial_Alta_Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_historial_alta_cliente;
	
	@JoinColumn(name = "fk_cliente", referencedColumnName = "id_cliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Cliente cliente;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date alta;
	
	private String descripcion;
	
	@JoinColumn(name = "fk_ctactecliente_cliente", referencedColumnName = "id_ctactecliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private CtaCteCliente ctactecliente;


	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public long getId_historial_alta_cliente() {
		return id_historial_alta_cliente;
	}

	public void setId_historial_alta_cliente(long id_historial_alta_cliente) {
		this.id_historial_alta_cliente = id_historial_alta_cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CtaCteCliente getCtactecliente() {
		return ctactecliente;
	}

	public void setCtactecliente(CtaCteCliente ctactecliente) {
		this.ctactecliente = ctactecliente;
	}
	
	

}
