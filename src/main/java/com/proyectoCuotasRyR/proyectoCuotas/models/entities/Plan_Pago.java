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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "planes_pagos")
public class Plan_Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_plan_pago;

	@JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Proveedor id_proveedor;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_plan_pago")
	@JsonManagedReference
	@JsonIgnore
	private List<Cuota> cuotas;

	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Cliente id_cliente;
	
	@JoinColumn(name = "id_sucursal", referencedColumnName = "id_sucursal")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Sucursal id_sucursal;
	
	
	@JoinColumn(name = "id_tipo_interes", referencedColumnName = "id_tipo_interes")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Tipo_Interes id_tipo_interes;
	
	private float total;
	
	private float valor_cuota;

	private long nro_cuotas;
	
	private float deuda_nominal;
	
	private float gasto;
	
	@Column(name="iva_sobre_interes")
	private float iva_interes;
	
	@Column(name="iva_sobre_honorarios")
	private float iva_honorarios;
	
	@Column(name="interes_mensual")
	private float interes_mensual;
	
	private float honorarios;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_inicio")
	private Date fecha_inicio;
	
	

	public Sucursal getId_sucursal() {
		return id_sucursal;
	}

	public void setId_sucursal(Sucursal id_sucursal) {
		this.id_sucursal = id_sucursal;
	}

	private String pactora;
	
	private String nro_expediente;

	private boolean activo;
	private boolean completado;
	private boolean irrecuperable;

	public long getId_plan_pago() {
		return id_plan_pago;
	}

	public void setId_plan_pago(long id_plan_pago) {
		this.id_plan_pago = id_plan_pago;
	}

	public Proveedor getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(Proveedor id_proveedor) {
		this.id_proveedor = id_proveedor;
	}



	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public boolean isIrrecuperable() {
		return irrecuperable;
	}

	public void setIrrecuperable(boolean irrecuperable) {
		this.irrecuperable = irrecuperable;
	}



	public String getPactora() {
		return pactora;
	}

	public void setPactora(String pactora) {
		this.pactora = pactora;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	

	public Cliente getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Cliente id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Tipo_Interes getId_tipo_interes() {
		return id_tipo_interes;
	}

	public void setId_tipo_interes(Tipo_Interes id_tipo_interes) {
		this.id_tipo_interes = id_tipo_interes;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getValor_cuota() {
		return valor_cuota;
	}

	public void setValor_cuota(float valor_cuota) {
		this.valor_cuota = valor_cuota;
	}

	public long getNro_cuotas() {
		return nro_cuotas;
	}

	public void setNro_cuotas(long nro_cuotas) {
		this.nro_cuotas = nro_cuotas;
	}

	public float getDeuda_nominal() {
		return deuda_nominal;
	}

	public void setDeuda_nominal(float deuda_nominal) {
		this.deuda_nominal = deuda_nominal;
	}

	public float getGasto() {
		return gasto;
	}

	public void setGasto(float gasto) {
		this.gasto = gasto;
	}

	public float getIva_interes() {
		return iva_interes;
	}

	public void setIva_interes(float iva_interes) {
		this.iva_interes = iva_interes;
	}

	public float getIva_honorarios() {
		return iva_honorarios;
	}

	public void setIva_honorarios(float iva_honorarios) {
		this.iva_honorarios = iva_honorarios;
	}

	public float getInteres_mensual() {
		return interes_mensual;
	}

	public void setInteres_mensual(float interes_mensual) {
		this.interes_mensual = interes_mensual;
	}

	public float getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(float honorarios) {
		this.honorarios = honorarios;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getNro_expediente() {
		return nro_expediente;
	}

	public void setNro_expediente(String nro_expediente) {
		this.nro_expediente = nro_expediente;
	}
	
	

}
