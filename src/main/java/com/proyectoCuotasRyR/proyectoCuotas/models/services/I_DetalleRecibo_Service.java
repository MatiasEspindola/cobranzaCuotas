package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Detalle_Recibo;

public interface I_DetalleRecibo_Service {

	public List<Detalle_Recibo> listar();
	
	public Detalle_Recibo buscarPorId(long id_detalle_recibo);
	
	public void guardar(Detalle_Recibo detalle_recibo);
	
}
