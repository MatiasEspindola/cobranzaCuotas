package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Pregunta;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Pregunta_Repo;

@Service
public class PreguntaService implements I_Pregunta_Service {

	@Autowired
	private I_Pregunta_Repo repo;
	
	@Override
	public List<Pregunta> listar() {
		// TODO Auto-generated method stub
		return (List<Pregunta>) repo.findAll();
	}

}
