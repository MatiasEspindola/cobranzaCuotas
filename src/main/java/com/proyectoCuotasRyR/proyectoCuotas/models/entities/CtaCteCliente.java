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
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	private float debe;
	
	private float haber;
	
	private float saldo;
	
	@JoinColumn(name = "id_cli", referencedColumnName = "id_cliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Cliente cliente;
	
	@JoinColumn(name = "id_comprobante", referencedColumnName = "id_comprobante")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Comprobante comprobante;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ctactecli")
	@JsonManagedReference
	@JsonIgnore
	private List<Recibo> recibos;
	

	public long getId_ctactecliente() {
		return id_ctactecliente;
	}

	public void setId_ctactecliente(long id_ctactecliente) {
		this.id_ctactecliente = id_ctactecliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}


	
	
}