package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Rol;

public interface I_Rol_Service {

	public Rol buscarPorId(long id_rol);
	
	public List<Rol> listarTodo();
	
}
