package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "importes")
public class Importe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_importe;

	@JoinColumn(name = "id_medio_pago", referencedColumnName = "id_medio_pago")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Medio_Pago medio_pago;

	@JoinColumn(name = "id_cuota", referencedColumnName = "id_cuota")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Cuota cuota;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_importe")
	@JsonManagedReference
	@JsonIgnore
	private List<Detalle_Recibo> detalles_recibos;

	private String detalles;

	private float importe;

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public Cuota getCuota() {
		return cuota;
	}

	public void setCuota(Cuota cuota) {
		this.cuota = cuota;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	/*
	 * public Cuota getCuota() { return cuota; }
	 * 
	 * public void setCuota(Cuota cuota) { this.cuota = cuota; }
	 */

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public long getId_importe() {
		return id_importe;
	}

	public void setId_importe(long id_importe) {
		this.id_importe = id_importe;
	}

	public Medio_Pago getMedio_pago() {
		return medio_pago;
	}

	public void setMedio_pago(Medio_Pago medio_pago) {
		this.medio_pago = medio_pago;
	}

	public List<Detalle_Recibo> getDetalles_recibos() {
		return detalles_recibos;
	}

	public void setDetalles_recibos(List<Detalle_Recibo> detalles_recibos) {
		this.detalles_recibos = detalles_recibos;
	}

}
