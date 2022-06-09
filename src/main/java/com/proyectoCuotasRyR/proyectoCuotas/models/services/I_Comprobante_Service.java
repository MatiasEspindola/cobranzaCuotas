package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Comprobante;

public interface I_Comprobante_Service {

	public List<Comprobante> listar();
	
	public Comprobante buscarPorId(long id_comprobante);
	
	public void guardar(Comprobante comprobante);
	
	public void eliminar(Comprobante comprobante);
	
}
