package com.proyectoCuotasRyR.proyectoCuotas.models.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="sucursales")
public class Sucursal {
	
	private long id_sucursal;
	
	private Empresa empresa;
	
	private String tel_fijo;
	
	private String tel_fijo2;
	
	private String cel;
	
	private String cel2;
	
	private String mail;
	
	private String mail2;
	
	private String razon_social;
	
	private String nombre_fantasia;
	
	private String direccion;
	
	private boolean es_casa_central;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inicio_actividades;
	
	private Usuario usuario;

}
