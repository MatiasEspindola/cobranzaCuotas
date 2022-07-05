package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Historial_Sucursal_Repo;

@Service
public class HistorialSucursalService implements I_Historial_Sucursal_Service {

	@Autowired
	private I_Historial_Sucursal_Repo repo;
	
	@Override
	public void guardar(Historial_Sucursal historial_sucursal) {
		// TODO Auto-generated method stub
		repo.save(historial_sucursal);
	}

	@Override
	public List<Historial_Sucursal> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Historial_Sucursal>) repo.findAll();
	}

	@Override
	public Historial_Sucursal buscarPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return repo.buscarPorSucursal(sucursal);
	}

}
