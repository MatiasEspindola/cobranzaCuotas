package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Plan_Pago_Repo;

@Service
public class Plan_Pago_Service implements I_Plan_Pago_Service {

	@Autowired
	private I_Plan_Pago_Repo repo;
	
	@Override
	public List<Plan_Pago> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Plan_Pago>) repo.findAll();
	}

	@Override
	public Plan_Pago buscarPorId(Long id_plan_pago) {
		// TODO Auto-generated method stub
		return repo.findById(id_plan_pago).orElse(null);
	}

	@Override
	public void guardar(Plan_Pago plan_pago) {
		// TODO Auto-generated method stub
		repo.save(plan_pago);
	}
	
	@Override
	public void eliminar(Plan_Pago plan_pago) {
		// TODO Auto-generated method stub
		repo.delete(plan_pago);
	}


}
