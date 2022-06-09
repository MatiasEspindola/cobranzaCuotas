package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;

public interface I_Plan_Pago_Service {

	public List<Plan_Pago> listarTodo();
	
	public Plan_Pago buscarPorId(Long id_plan_pago);
	
	public void guardar(Plan_Pago plan_pago);
        
	public void eliminar(Plan_Pago plan_pago);
	
}
