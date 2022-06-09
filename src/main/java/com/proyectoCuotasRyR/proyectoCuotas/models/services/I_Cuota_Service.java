package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;

public interface I_Cuota_Service {

	public List<Cuota> listarTodo();
	
	public Cuota buscarPorId(Long id_cuota);
	
	public void guardar(Cuota cuota);
	
	public void borrarTodo(List<Cuota> cuotas);
	
	public void borrar(Cuota cuota);
	
}
