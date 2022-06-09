package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Cuota_Repo;

@Service
public class CuotaService implements I_Cuota_Service {

	@Autowired
	private I_Cuota_Repo repo;
	
	@Override
	public List<Cuota> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Cuota>) repo.findAll();
	}

	@Override
	public Cuota buscarPorId(Long id_cuota) {
		// TODO Auto-generated method stub
		return repo.findById(id_cuota).orElse(null);
	}

	@Override
	public void guardar(Cuota cuota) {
		// TODO Auto-generated method stub
		repo.save(cuota);
	}

	@Override
	public void borrarTodo(List<Cuota> cuotas) {
		// TODO Auto-generated method stub
		repo.deleteAll(cuotas);
	}

	@Override
	public void borrar(Cuota cuota) {
		// TODO Auto-generated method stub
		repo.delete(cuota);
	}

}
