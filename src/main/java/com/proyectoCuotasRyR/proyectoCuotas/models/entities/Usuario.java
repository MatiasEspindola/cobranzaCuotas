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
@Table(name="usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_usuario;
	
	private String username;

	private String password;
	
	private String pass2;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_alta;
	
	private boolean user_principal;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_usuario_auth")
	@JsonManagedReference
	@JsonIgnore
	private List<Authority> authorities;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "fk_usuario")
	@JsonManagedReference
	@JsonIgnore
	private List<Usuario_Sucursal> usuarios_sucursales;
	
	
	
	@JoinColumn(name = "id_pregunta", referencedColumnName = "id_pregunta")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Pregunta id_pregunta;
	
	private String respuesta;
	
	private boolean activo;
	
	

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}


	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public Pregunta getId_pregunta() {
		return id_pregunta;
	}

	public void setId_pregunta(Pregunta id_pregunta) {
		this.id_pregunta = id_pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public List<Usuario_Sucursal> getUsuarios_sucursales() {
		return usuarios_sucursales;
	}

	public void setUsuarios_sucursales(List<Usuario_Sucursal> usuarios_sucursales) {
		this.usuarios_sucursales = usuarios_sucursales;
	}

	public boolean isUser_principal() {
		return user_principal;
	}

	public void setUser_principal(boolean user_principal) {
		this.user_principal = user_principal;
	}

	
	
	
}
