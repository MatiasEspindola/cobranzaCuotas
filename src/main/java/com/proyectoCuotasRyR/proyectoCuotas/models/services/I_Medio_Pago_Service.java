package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Medio_Pago;

public interface I_Medio_Pago_Service {

	public List<Medio_Pago> listar();
	
	public Medio_Pago buscarPorId(long id_medio_pago);
	
}
