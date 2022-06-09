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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "cuotas")
public class Cuota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_cuota;

	@Column(name = "nro_cuota")
	private long nro_cuota;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	private boolean pagado;

	private boolean refinanciado;

	private boolean vencida;
	
	private float pendiente;
	
	

	public float getPendiente() {
		return pendiente;
	}

	public void setPendiente(float pendiente) {
		this.pendiente = pendiente;
	}

	@Column(name = "deuda_nominal")
	private float deuda_nominal;

	@Column(name = "iva_interes")
	private float iva_interes;

	@Column(name = "iva_honorarios")
	private float iva_honorarios;

	private float honorarios;

	@Column(name = "cuota_nominal")
	private float cuota_nominal;

	private float interes;

	private float valor;

	private float gastos;


	@JoinColumn(name = "id_plan_pago", referencedColumnName = "id_plan_pago")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Plan_Pago id_plan_pago;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_cuota")
	@JsonManagedReference
	@JsonIgnore
	private List<Importe> importes;

	public long getId_cuota() {
		return id_cuota;
	}

	public void setId_cuota(long id_cuota) {
		this.id_cuota = id_cuota;
	}

	public long getNro_cuota() {
		return nro_cuota;
	}

	public void setNro_cuota(long nro_cuota) {
		this.nro_cuota = nro_cuota;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public boolean isRefinanciado() {
		return refinanciado;
	}

	public void setRefinanciado(boolean refinanciado) {
		this.refinanciado = refinanciado;
	}

	public boolean isVencida() {
		return vencida;
	}

	public void setVencida(boolean vencida) {
		this.vencida = vencida;
	}

	public float getDeuda_nominal() {
		return deuda_nominal;
	}

	public void setDeuda_nominal(float deuda_nominal) {
		this.deuda_nominal = deuda_nominal;
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

	public float getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(float honorarios) {
		this.honorarios = honorarios;
	}

	public float getCuota_nominal() {
		return cuota_nominal;
	}

	public void setCuota_nominal(float cuota_nominal) {
		this.cuota_nominal = cuota_nominal;
	}

	public float getInteres() {
		return interes;
	}

	public void setInteres(float interes) {
		this.interes = interes;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getGastos() {
		return gastos;
	}

	public void setGastos(float gastos) {
		this.gastos = gastos;
	}

	public Plan_Pago getId_plan_pago() {
		return id_plan_pago;
	}

	public void setId_plan_pago(Plan_Pago id_plan_pago) {
		this.id_plan_pago = id_plan_pago;
	}

	public List<Importe> getImportes() {
		return importes;
	}

	public void setImportes(List<Importe> importes) {
		this.importes = importes;
	}

	public float sum_importes() {
		
		float imp = 0;
		
		for(Importe impo : importes) {
			imp += impo.getImporte();
		}
		
		return imp;
	}

}
