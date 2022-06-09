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
@Table(name="authorities")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JoinColumn(name = "id_usuario_auth", referencedColumnName = "id_usuario")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Usuario id_usuario_auth;
	
	@JoinColumn(name = "id_rol_auth", referencedColumnName = "id_rol")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Rol id_rol_auth;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Usuario getId_usuario_auth() {
		return id_usuario_auth;
	}

	public void setId_usuario_auth(Usuario id_usuario_auth) {
		this.id_usuario_auth = id_usuario_auth;
	}

	public Rol getId_rol_auth() {
		return id_rol_auth;
	}

	public void setId_rol_auth(Rol id_rol_auth) {
		this.id_rol_auth = id_rol_auth;
	}
	
}
