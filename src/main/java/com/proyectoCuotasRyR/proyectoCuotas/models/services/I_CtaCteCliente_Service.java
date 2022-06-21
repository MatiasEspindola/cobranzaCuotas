package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;

public interface I_CtaCteCliente_Service {

	public List<CtaCteCliente> listar();
	
	public CtaCteCliente buscarPorId(long id_ctactecliente);
	
	public void eliminar(CtaCteCliente ctactecliente);
	
	public void guardar(CtaCteCliente ctactecliente);
	
	public List<CtaCteCliente> buscarPorCliente(Cliente cliente);
	
}
