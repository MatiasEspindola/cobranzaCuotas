package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Responsable_Iva;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Responsable_Iva_Repo;

@Service
public class ResponsableService implements I_Responsable_Iva_Service {

	@Autowired
	private I_Responsable_Iva_Repo repo;
	
	@Override
	public List<Responsable_Iva> listar_todo() {
		// TODO Auto-generated method stub
		return (List<Responsable_Iva>) repo.findAll();
	}

	@Override
	public Responsable_Iva buscarPorId(long id_responsable) {
		// TODO Auto-generated method stub
		return repo.findById(id_responsable).orElse(null);
	}

	@Override
	public void guardar(Responsable_Iva responsable_iva) {
		// TODO Auto-generated method stub
		repo.save(responsable_iva);
	}

}
