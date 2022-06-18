package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Actividad_Repo;

@Service
public class ActividadService implements I_Actividad_Service {
	
	@Autowired
	private I_Actividad_Repo actividad_repo;

	@Override
	public void borrar_actividad(Actividad_Usuario actividad) {
		// TODO Auto-generated method stub
		actividad_repo.delete(actividad);
	}

	@Override
	public void guardar_actividad(Actividad_Usuario actividad) {
		// TODO Auto-generated method stub
		actividad_repo.save(actividad);
	}

}
