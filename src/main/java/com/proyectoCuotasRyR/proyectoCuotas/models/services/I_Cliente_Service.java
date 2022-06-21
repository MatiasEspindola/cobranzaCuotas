package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Alta_Cliente;

public interface I_Cliente_Service {

	public List<Cliente> listarTodo();
	
	public List<Cliente> buscarPorTerm(String term);
	
	public Cliente buscarPorId(Long id_cliente);
	
	public void guardar(Cliente cliente, boolean activo);
	
	public void deshabilitar(Cliente cliente, boolean valor);
	
	public boolean existente(Cliente cliente, boolean valor);
	
	public void borrar_historial(Historial_Alta_Cliente historial);
	
	public void guardar_historial(Historial_Alta_Cliente historial);
	
	public Historial_Alta_Cliente buscarPorCtaCte(CtaCteCliente ctactecliente);
}
