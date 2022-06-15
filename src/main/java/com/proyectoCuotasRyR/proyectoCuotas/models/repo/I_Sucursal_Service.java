package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;

public interface I_Sucursal_Service {

	public List<Sucursal> listar();
	
	public Sucursal buscarPorId(long id_sucursal);
	
	public void guardar(Sucursal sucursal, boolean valor);
	
	public void deshabilitar(Sucursal sucursal, boolean valor);
	
	public boolean existente(Sucursal sucursal, boolean valor);
}
