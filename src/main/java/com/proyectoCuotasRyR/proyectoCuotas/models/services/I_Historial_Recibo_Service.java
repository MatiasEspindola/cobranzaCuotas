package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Recibo;

public interface I_Historial_Recibo_Service {

	public void guardar(Historial_Recibo historial_recibo);
	
	public List<Historial_Recibo> listarTodo();
	
	
}
