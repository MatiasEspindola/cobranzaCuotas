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
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date hora;
	
	private String descripcion;
	
	@JoinColumn(name = "id_ctactecli", referencedColumnName = "id_ctactecliente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private CtaCteCliente id_ctactecliente;
	
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CtaCteCliente getId_ctactecliente() {
		return id_ctactecliente;
	}

	public void setId_ctactecliente(CtaCteCliente id_ctactecliente) {
		this.id_ctactecliente = id_ctactecliente;
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