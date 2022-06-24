package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	

}
