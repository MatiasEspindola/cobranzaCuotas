package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Historial_Plan_Pago_Repo;

@Service
public class HistorialPlan_PagoService implements I_Historial_Plan_Pago_Service {

	@Autowired
	private I_Historial_Plan_Pago_Repo repo;
	
	@Override
	public List<Historial_Plan_Pago> listar() {
		// TODO Auto-generated method stub
		return (List<Historial_Plan_Pago>) repo.findAll();
	}

	@Override
	public void guardar(Historial_Plan_Pago historial_plan_pago) {
		// TODO Auto-generated method stub
		repo.save(historial_plan_pago);
	}

}
