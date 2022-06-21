package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Plan_Pago;

public interface I_Historial_Plan_Pago_Service {

	public List<Historial_Plan_Pago> listar();
	
	public void guardar(Historial_Plan_Pago historial_plan_pago);
	
}
