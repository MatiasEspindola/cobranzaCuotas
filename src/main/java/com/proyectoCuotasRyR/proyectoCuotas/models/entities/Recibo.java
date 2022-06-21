package com.proyectoCuotasRyR.proyectoCuotas.models.entities;
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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="recibos")
public class Recibo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_recibo;
	
	
	private String descripcion;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_recibo")
	@JsonManagedReference
	@JsonIgnore
	private List<Historial_Recibo> historiales;
	
	
	
	public List<Historial_Recibo> getHistoriales() {
		return historiales;
	}

	public void setHistoriales(List<Historial_Recibo> historiales) {
		this.historiales = historiales;
	}

	@JoinColumn(name = "id_concepto", referencedColumnName = "id_concepto")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Concepto concepto;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_recibo")
	@JsonManagedReference
	@JsonIgnore
	private List<Detalle_Recibo> detalles_recibos;

	public long getId_recibo() {
		return id_recibo;
	}

	public void setId_recibo(long id_recibo) {
		this.id_recibo = id_recibo;
	}

	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	public List<Detalle_Recibo> getDetalles_recibos() {
		return detalles_recibos;
	}

	public void setDetalles_recibos(List<Detalle_Recibo> detalles_recibos) {
		this.detalles_recibos = detalles_recibos;
	}
	
	public float getTotal() {
		float total = 0;
		for(Detalle_Recibo detalle_recibo : detalles_recibos) {
			total += detalle_recibo.getImporte().getImporte();
		}
		
		return total;
	}
	
}