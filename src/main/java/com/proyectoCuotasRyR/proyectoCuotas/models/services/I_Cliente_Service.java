package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;

public interface I_Cliente_Service {

	public List<Cliente> listarTodo();
	
	public List<Cliente> buscarPorTerm(String term);
	
	public Cliente buscarPorId(Long id_cliente);
	
	public void guardar(Cliente cliente);
	
	public void eliminar(Cliente cliente);
	
}
