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
@Table(name="usuarios_sucursales")
public class Usuario_Sucursal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_usuario_sucursal;
	
	@JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Usuario usuario;
	
	@JoinColumn(name = "fk_sucursal", referencedColumnName = "id_sucursal")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Sucursal sucursal;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_usuario_sucursal")
	@JsonManagedReference
	@JsonIgnore
	private List<Actividad_Usuario> actividades;

	public long getId_usuario_sucursal() {
		return id_usuario_sucursal;
	}

	public void setId_usuario_sucursal(long id_usuario_sucursal) {
		this.id_usuario_sucursal = id_usuario_sucursal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Actividad_Usuario> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad_Usuario> actividades) {
		this.actividades = actividades;
	}
	
	
	

}
