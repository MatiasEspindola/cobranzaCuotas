package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;

public interface I_Proveedor_Service {

	public List<Proveedor> listarTodo();
	
	public Proveedor buscarPorId(Long id_proveedor);
	
	public void guardar(Proveedor proveedor);
	
	public List<Proveedor> buscarPorTerm(String term);
	
	public void eliminar(Proveedor proveedor);
	
}
