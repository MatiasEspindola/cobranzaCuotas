package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Interes;

public interface I_Tipo_Interes_Service {

	public List<Tipo_Interes> listarTodo();
	
	public Tipo_Interes buscarPorId(Long id_tipo_interes);
	
	public void guardar(Tipo_Interes tipo_interes);
	
}
