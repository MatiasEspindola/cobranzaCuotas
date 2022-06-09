package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Responsable_Iva;

public interface I_Responsable_Iva_Service {

	public List<Responsable_Iva> listar_todo();
	
	public Responsable_Iva buscarPorId(long id_responsable);
	
	public void guardar(Responsable_Iva responsable_iva);
	
}
