package com.proyectoCuotasRyR.proyectoCuotas.models.entities;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="ctasctescliente")
public class CtaCteCliente{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_ctactecliente;
	
	private float debe;
	
	private float haber;
	
	private float saldo;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ctactecliente_cliente")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Alta_Cliente> historiales_altas_clientes;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ctactecliente_plan_pago")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Plan_Pago> historiales_planes_pagos;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ctactecliente_recibo")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Recibo> historiales_recibos;
	
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Cliente cliente;
	
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public long getId_ctactecliente() {
		return id_ctactecliente;
	}

	public void setId_ctactecliente(long id_ctactecliente) {
		this.id_ctactecliente = id_ctactecliente;
	}

	public float getDebe() {
		return debe;
	}

	public void setDebe(float debe) {
		this.debe = debe;
	}

	public float getHaber() {
		return haber;
	}

	public void setHaber(float haber) {
		this.haber = haber;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public List<Historial_Alta_Cliente> getHistoriales_altas_clientes() {
		return historiales_altas_clientes;
	}

	public void setHistoriales_altas_clientes(List<Historial_Alta_Cliente> historiales_altas_clientes) {
		this.historiales_altas_clientes = historiales_altas_clientes;
	}

	public List<Historial_Plan_Pago> getHistoriales_planes_pagos() {
		return historiales_planes_pagos;
	}

	public void setHistoriales_planes_pagos(List<Historial_Plan_Pago> historiales_planes_pagos) {
		this.historiales_planes_pagos = historiales_planes_pagos;
	}

	public List<Historial_Recibo> getHistoriales_recibos() {
		return historiales_recibos;
	}

	public void setHistoriales_recibos(List<Historial_Recibo> historiales_recibos) {
		this.historiales_recibos = historiales_recibos;
	}
	
}