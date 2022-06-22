package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Historial_Recibo_Repo;

@Service
public class HistorialReciboService implements I_Historial_Recibo_Service {

	@Autowired
	private I_Historial_Recibo_Repo repo;
	
	@Override
	public void guardar(Historial_Recibo historial_recibo) {
		// TODO Auto-generated method stub
		repo.save(historial_recibo);
	}

	@Override
	public List<Historial_Recibo> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Historial_Recibo>) repo.findAll();
	}

	@Override
	public Historial_Recibo buscarPorRecibo(Recibo recibo) {
		// TODO Auto-generated method stub
		return repo.buscarPorRecibo(recibo);
	}

}
