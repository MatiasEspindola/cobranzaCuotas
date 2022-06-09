package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="comprobantes")
public class Comprobante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_comprobante;
	
	private String comprobante;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_comprobante")
	@JsonManagedReference
	@JsonIgnore
	List<CtaCteCliente> ctas_ctes_cliente;
	
	@JoinColumn(name = "id_tipo_comprobante", referencedColumnName = "id_tipo_comprobante")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Tipo_Comprobante tipo_comprobante;

	public long getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(long id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public List<CtaCteCliente> getCtas_ctes_cliente() {
		return ctas_ctes_cliente;
	}

	public void setCtas_ctes_cliente(List<CtaCteCliente> ctas_ctes_cliente) {
		this.ctas_ctes_cliente = ctas_ctes_cliente;
	}

	public Tipo_Comprobante getTipo_comprobante() {
		return tipo_comprobante;
	}

	public void setTipo_comprobante(Tipo_Comprobante tipo_comprobante) {
		this.tipo_comprobante = tipo_comprobante;
	}
	
}
