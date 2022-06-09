package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="tipos_intereses")
public class Tipo_Interes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_tipo_interes;
	
	@Column(name="tipo_interes")
	private String tipo_interes;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_tipo_interes")
	@JsonManagedReference
	@JsonIgnore
	private List<Plan_Pago> planes_pagos;

	public long getId_tipo_interes() {
		return id_tipo_interes;
	}

	public void setId_tipo_interes(long id_tipo_interes) {
		this.id_tipo_interes = id_tipo_interes;
	}

	public String getTipo_interes() {
		return tipo_interes;
	}

	public void setTipo_interes(String tipo_interes) {
		this.tipo_interes = tipo_interes;
	}

	public List<Plan_Pago> getPlanes_pagos() {
		return planes_pagos;
	}

	public void setPlanes_pagos(List<Plan_Pago> planes_pagos) {
		this.planes_pagos = planes_pagos;
	}

}
