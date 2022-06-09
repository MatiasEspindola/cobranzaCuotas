package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;

public interface I_Recibo_Service {
	
	public List<Recibo> listar();
	
	public Recibo buscarPorId(long id_recibo);
	
	public void borrar(Recibo recibo);
	
	public void guardar(Recibo recibo);
	
}
