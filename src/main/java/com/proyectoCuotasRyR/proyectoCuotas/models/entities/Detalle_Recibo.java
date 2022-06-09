package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.sql.Time;
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
@Table(name="detalles_recibos")
public class Detalle_Recibo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_detalle_recibo;

	
	
	
	@JoinColumn(name = "id_recibo", referencedColumnName = "id_recibo")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Recibo recibo;
	
	//private String descripcion;
	
	@JoinColumn(name = "id_importe", referencedColumnName = "id_importe")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Importe importe;
	
	

	public long getId_detalle_recibo() {
		return id_detalle_recibo;
	}

	public void setId_detalle_recibo(long id_detalle_recibo) {
		this.id_detalle_recibo = id_detalle_recibo;
	}
	
	public Recibo getRecibo() {
		return recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	/*public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}*/

	

	public Importe getImporte() {
		return importe;
	}

	public void setImporte(Importe importe) {
		this.importe = importe;
	}

	
}
