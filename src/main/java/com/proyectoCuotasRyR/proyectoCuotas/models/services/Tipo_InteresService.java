package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Interes;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Tipo_Interes_Repo;

@Service
public class Tipo_InteresService implements I_Tipo_Interes_Service {

	@Autowired
	private I_Tipo_Interes_Repo tipoInteresRepo;
	
	@Override
	public List<Tipo_Interes> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Tipo_Interes>) tipoInteresRepo.findAll();
	}

	@Override
	public Tipo_Interes buscarPorId(Long id_tipo_interes) {
		// TODO Auto-generated method stub
		return tipoInteresRepo.findById(id_tipo_interes).orElse(null);
	}

	@Override
	public void guardar(Tipo_Interes tipo_interes) {
		// TODO Auto-generated method stub
		tipoInteresRepo.save(tipo_interes);
	}

}
