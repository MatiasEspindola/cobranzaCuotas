package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Alta_Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;

public interface I_Proveedor_Service {

	public List<Proveedor> listarTodo();
	
	public Proveedor buscarPorId(Long id_proveedor);
	
	public void guardar(Proveedor proveedor, boolean valor);
	
	public List<Proveedor> buscarPorTerm(String term);
	
	public void deshabilitar(Proveedor proveedor, boolean valor);
	
	public boolean existente(Proveedor proveedor, boolean valor);
	
	public void borrar_historial(Historial_Proveedor historial);
	
	public void guardar_historial(Historial_Proveedor historial);
	
}
